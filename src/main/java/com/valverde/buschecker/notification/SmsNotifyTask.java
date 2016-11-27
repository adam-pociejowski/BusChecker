package com.valverde.buschecker.notification;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class SmsNotifyTask {

    final String SMS_TEMPLATE = "BusTracker\n";

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 12 * * *")
    public void sendSmsNotifications() {
        final String POLAND_NUMBER = "48";

        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            System.out.println(user.getUsername());
//            List<BusDriver> buses = user.getBuses();
//            for (BusDriver bus : buses) {
//                String phone = POLAND_NUMBER + bus.getPhoneNumber();
//                sendNotificationIfNecessary(bus.getTechnicalReviewDate(), phone, ReviewTypes.TECHNICAL, notification);
//                sendNotificationIfNecessary(bus.getTachographReviewDate(), phone, ReviewTypes.TACHOGRAPH, notification);
//                sendNotificationIfNecessary(bus.getLiftReviewDate(), phone, ReviewTypes.LIFT, notification);
//                sendNotificationIfNecessary(bus.getExtinguisherReviewDate(), phone, ReviewTypes.EXTIGUISHERY, notification);
//                sendNotificationIfNecessary(bus.getInsuranceDate(), phone, ReviewTypes.INSURANCE, notification);
//            }
        }
    }

    private void sendNotificationIfNecessary(Date reviewDate, String phone, ReviewTypes type, int notification) {
        if (phone != null) {
            if (phone.length() == 11) {
                if (shouldSendNotification(reviewDate, type.getMonth(), notification)) {

                    new SmsClient().sendMessage(phone, SMS_TEMPLATE +type.getName()+"\n"+
                            getDateAsString(getNextReviewDate(reviewDate, type.getMonth())));
                }
            }
        }
    }

    boolean shouldSendNotification(Date reviewDate, int monthAmount, int notification) {
        if (reviewDate == null || monthAmount <= 0) {
            return false;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(getNextReviewDate(reviewDate, monthAmount));
        c.add(Calendar.DATE, -1 * notification);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());

        /**
        * Check if today is notification day
        */
        if ((c.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) &&
                c.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    Date getNextReviewDate(Date reviewDate, int monthAmount) {
        Calendar c = Calendar.getInstance();
        c.setTime(reviewDate);
        c.add(Calendar.MONTH, monthAmount);
        return c.getTime();
    }

    String getDateAsString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

    private enum ReviewTypes {
        TECHNICAL(6, "Przegląd techniczny"),
        LIFT(24, "Przegląd windy"),
        INSURANCE(12, "Ubezpieczenie"),
        EXTIGUISHERY(12, "Przegląd gaśnicy"),
        TACHOGRAPH(24, "Przegląd tachografu");

        @Getter
        private Integer month;

        @Getter
        private String name;

        ReviewTypes(Integer month, String name) {
            this.month = month;
            this.name = name;
        }
    }
}