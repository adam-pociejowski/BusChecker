package com.valverde.buschecker.notification.sms;

import com.valverde.buschecker.notification.sms.SmsNotifyTask;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

public class NotificationTest {
    private SmsNotifyTask task;
    private int month = 12;
    private int notification = 14;
    private Calendar c;

    public NotificationTest() {
        task = new SmsNotifyTask(null, null, null);
    }

    @Before
    public void setup() {
        c = Calendar.getInstance();
        c.setTime(new Date());
    }

    @Test
    public void shouldSendNotification() {
        /**
        * Last review was 1 year ago minus notification time
        **/
        c.add(Calendar.MONTH, -1 * month);
        c.add(Calendar.DATE, notification);
        Date lastReviewDate = c.getTime();

        Boolean shouldSend = task.shouldSendNotification(lastReviewDate, month, notification);
        assertTrue(shouldSend);
    }

    @Test
    public void shouldntSendNotificationInNextReviewDay() {
        /**
         * Last review was 1 year ago
         **/
        c.add(Calendar.MONTH, -1 * month);
        Date lastReviewDate = c.getTime();

        Boolean shouldSend = task.shouldSendNotification(lastReviewDate, month, notification);
        assertFalse(shouldSend);
    }

    @Test
    public void shouldntSendNotificationBeforeCorrectDay() {
        c.add(Calendar.MONTH, -1 * month);
        c.add(Calendar.DATE, notification + 1);
        Date lastReviewDate = c.getTime();

        Boolean shouldSend = task.shouldSendNotification(lastReviewDate, month, notification);
        assertFalse(shouldSend);
    }

    @Test
    public void shouldntSendNotificationOnNullLastReviewDate() {
        Boolean shouldSend = task.shouldSendNotification(null, month, notification);
        assertFalse(shouldSend);
    }

    @Test
    public void shouldntSendNotificationOnMinusNotificationDays() {
        Boolean shouldSend = task.shouldSendNotification(null, month, -1);
        assertFalse(shouldSend);
    }
}