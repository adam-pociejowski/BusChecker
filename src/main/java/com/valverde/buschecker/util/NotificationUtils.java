package com.valverde.buschecker.util;

import com.valverde.buschecker.enums.ReviewType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotificationUtils {

    private final static Integer POLISH_MOBILE_PHONE_DIGITS_AMOUNT = 9;

    public static boolean shouldSendNotification(Date reviewDate, int monthAmount, int notification) {
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


    public static Boolean hasCorrectPhoneNumber(String phoneNumber) {
        return phoneNumber != null && StringUtils.isNumeric(phoneNumber) && phoneNumber.length() == POLISH_MOBILE_PHONE_DIGITS_AMOUNT;
    }

    public static String getSmsMessage(ReviewType type, Date date) {
        String message = "BusChecker\n";
        message += type.getName()+"\n";
        message += getDateAsString(getNextReviewDate(date, type.getMonth()));
        return message;
    }

    public static Date getNextReviewDate(Date reviewDate, int monthAmount) {
        Calendar c = Calendar.getInstance();
        c.setTime(reviewDate);
        c.add(Calendar.MONTH, monthAmount);
        return c.getTime();
    }

    private static String getDateAsString(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }
}
