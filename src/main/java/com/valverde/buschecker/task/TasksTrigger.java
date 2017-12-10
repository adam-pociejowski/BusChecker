package com.valverde.buschecker.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TasksTrigger {

    @Scheduled(cron = "0 0 12 * * *")
    public void triggerSmsNotificationTask() {
        smsNotificationTask.startTask();
    }

    @Autowired
    public TasksTrigger(SmsNotificationTask smsNotificationTask) {
        this.smsNotificationTask = smsNotificationTask;
    }

    private final SmsNotificationTask smsNotificationTask;
}
