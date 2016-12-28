package com.valverde.buschecker.notification.sms;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.Notification;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.enums.Task;
import com.valverde.buschecker.notification.enums.NotificationState;
import com.valverde.buschecker.notification.sms.enums.ReviewType;
import com.valverde.buschecker.repository.UserRepository;
import com.valverde.buschecker.service.NotificationService;
import com.valverde.buschecker.service.TaskReportService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import static com.valverde.buschecker.notification.sms.enums.ReviewType.*;

@Component
@CommonsLog
public class SmsNotifyTask {

    private final static String SMS_TEMPLATE = "BusChecker\n";

    private final static Integer POLISH_MOBILE_PHONE_DIGITS_AMOUNT = 9;

    private User currentUser;

    private Integer correctlySent;

    private Integer notSent;

    private final UserRepository userRepository;

    private final NotificationService notificationService;

    private final TaskReportService taskReportService;

    @Autowired
    public SmsNotifyTask(UserRepository userRepository, NotificationService notificationService,
                         TaskReportService taskReportService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.taskReportService = taskReportService;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void sendSmsNotifications() {
        correctlySent = 0;
        notSent = 0;
        final String POLISH_PREFIX = "48";
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            currentUser = user;
            List<Driver> drivers = user.getDrivers();
            for (Driver driver : drivers) {
                sendNotificationsToDriver(POLISH_PREFIX, driver);
            }
        }
        taskReportService.save(Task.SMS_NOTIFICATION, correctlySent, notSent);
    }

    private void sendNotificationsToDriver(String POLISH_PREFIX, Driver driver) {
        if (correctPhoneNumber(driver.getPhoneNumber())) {
            String phoneNumber = POLISH_PREFIX + driver.getPhoneNumber();
            for (Bus bus : driver.getBuses()) {
                sendNotificationsIfNecessary(driver, phoneNumber, bus);
            }
        }
    }

    private void sendNotificationsIfNecessary(Driver driver, String phoneNumber, Bus bus) {
        sendNotificationIfNecessary(bus.getTechnicalReviewDate(), phoneNumber, TECHNICAL, driver);
        sendNotificationIfNecessary(bus.getTachographReviewDate(), phoneNumber, TACHOGRAPH, driver);
        sendNotificationIfNecessary(bus.getLiftReviewDate(), phoneNumber, LIFT, driver);
        sendNotificationIfNecessary(bus.getExtinguisherReviewDate(), phoneNumber, EXTIGUISHERY, driver);
        sendNotificationIfNecessary(bus.getInsuranceDate(), phoneNumber, INSURANCE, driver);
    }

    private void sendNotificationIfNecessary(Date reviewDate, String phone, ReviewType type, Driver driver) {
        if (shouldSendNotification(reviewDate, type.getMonth(), driver.getNotificationBefore())) {
            Notification notification = new Notification();
            notification.setDriver(driver);
            notification.setUser(currentUser);
            notification.setPhoneNumber(phone);
            try {
                new SmsClient().sendMessage(phone, SMS_TEMPLATE + type.getName() + "\n" +
                        getDateAsString(getNextReviewDate(reviewDate, type.getMonth())));
                notification.setState(NotificationState.SUCCESS);
                log.info("Sms notification sent to "+phone);
                correctlySent++;
            } catch (Exception e) {
                notification.setState(NotificationState.ERROR);
                log.error("Problem while sending sms notification to "+phone, e);
                notSent++;
            }
            notificationService.save(notification);
        }
    }

    private Boolean correctPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.length() == POLISH_MOBILE_PHONE_DIGITS_AMOUNT;
    }

    boolean shouldSendNotification(Date reviewDate, int monthAmount, int notification) {
        if (reviewDate == null || monthAmount <= 0)
            return false;

        Calendar c = Calendar.getInstance();
        c.setTime(getNextReviewDate(reviewDate, monthAmount));
        c.add(Calendar.DATE, -1 * notification);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        /* Check if today is notification day */
        return (c.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
                c.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private Date getNextReviewDate(Date reviewDate, int monthAmount) {
        Calendar c = Calendar.getInstance();
        c.setTime(reviewDate);
        c.add(Calendar.MONTH, monthAmount);
        return c.getTime();
    }

    private String getDateAsString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
}