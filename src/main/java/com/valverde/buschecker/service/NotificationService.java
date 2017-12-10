package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.Notification;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.enums.NotificationState;
import com.valverde.buschecker.enums.ReviewType;
import com.valverde.buschecker.repository.NotificationRepository;
import com.valverde.buschecker.util.NotificationUtils;
import com.valverde.buschecker.web.dto.SmsDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.valverde.buschecker.enums.ReviewType.*;
import static com.valverde.buschecker.enums.ReviewType.INSURANCE;

@Service
@CommonsLog
@Transactional
public class NotificationService {

    public List<SmsDTO> getMessagesToSend(Driver driver, User user) {
        List<SmsDTO> messagesToSend = new ArrayList<>();
        if (NotificationUtils.hasCorrectPhoneNumber(driver.getPhoneNumber())) {
            for (Bus bus : driver.getBuses()) {
                addMessage(messagesToSend, getMessageIfShouldSend(bus.getTechnicalReviewDate(), TECHNICAL, driver, user));
                addMessage(messagesToSend, getMessageIfShouldSend(bus.getTachographReviewDate(), TACHOGRAPH, driver, user));
                addMessage(messagesToSend, getMessageIfShouldSend(bus.getLiftReviewDate(), LIFT, driver, user));
                addMessage(messagesToSend, getMessageIfShouldSend(bus.getExtinguisherReviewDate(), EXTIGUISHERY, driver, user));
                addMessage(messagesToSend, getMessageIfShouldSend(bus.getInsuranceDate(), INSURANCE, driver, user));
            }
        }
        return messagesToSend;
    }

    private SmsDTO getMessageIfShouldSend(Date reviewDate, ReviewType type, Driver driver, User user) {
        SmsDTO sms = null;
        if (NotificationUtils.shouldSendNotification(reviewDate, type.getMonth(), driver.getNotificationBefore())) {
            saveNotification(driver, user);
            sms = new SmsDTO();
            sms.setRecipients(POLISH_PREFIX + driver.getPhoneNumber());
            sms.setMessage(NotificationUtils.getSmsMessage(type, reviewDate));
        }
        return sms;
    }

    private void addMessage(List<SmsDTO> messagesToSend, SmsDTO message) {
        if (message != null)
            messagesToSend.add(message);
    }

    private void saveNotification(Driver driver, User user) {
        Notification notification = new Notification();
        notification.setDriver(driver);
        notification.setUser(user);
        notification.setPhoneNumber(POLISH_PREFIX + driver.getPhoneNumber());
        notification.setState(NotificationState.SUCCESS);
        notification.setDate(new Date());
        notificationRepository.save(notification);
    }

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    private final NotificationRepository notificationRepository;

    private final static String POLISH_PREFIX = "48";
}