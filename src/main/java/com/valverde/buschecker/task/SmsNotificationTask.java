package com.valverde.buschecker.task;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.notifier.NotifierService;
import com.valverde.buschecker.notifier.NotifyStatus;
import com.valverde.buschecker.service.NotificationService;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.web.dto.SmsDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@CommonsLog
public class SmsNotificationTask extends AbstractTask {

    @Override
    void startTask() {
        try {
            logStartTask();
            Iterable<User> users = userService.getUsers();
            List<SmsDTO> messagesToSend = new ArrayList<>();
            for (User user : users) {
                for (Driver driver : user.getDrivers()) {
                    messagesToSend.addAll(notificationService.getMessagesToSend(driver, user));
                }
            }
            sendSmsMessages(messagesToSend);
        } catch (Exception e) {
            log.error("Problem in "+getTaskName(), e);
        } finally {
            logEndTask();
        }
    }

    private void sendSmsMessages(List<SmsDTO> messagesToSend) {
        String username = env.getProperty("notifier.service.username");
        String password = env.getProperty("notifier.service.password");
        NotifierService notifierService = new NotifierService(username, password);
        for (SmsDTO sms : messagesToSend) {
            sendSms(notifierService, sms);
        }
    }

    private void sendSms(NotifierService notifierService, SmsDTO sms) {
        NotifyStatus status = notifierService.sendSms(sms.getMessage(), sms.getRecipients());
        if (status == NotifyStatus.SUCCESS) {
            log.info("Sms message sent to: "+sms.getRecipients());
        } else {
            log.error("Problem while trying to send sms to: "+sms.getRecipients());
        }
    }

    @Autowired
    public SmsNotificationTask(UserService userService, NotificationService notificationService, Environment env) {
        this.userService = userService;
        this.notificationService = notificationService;
        this.env = env;
    }

    private final UserService userService;

    private final NotificationService notificationService;

    private final Environment env;
}
