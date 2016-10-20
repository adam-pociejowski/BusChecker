package com.valverde.buschecker.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date stringToDate(String dateString, String dateFormat) {
        Date date = null;
        if (!dateString.equals("")) {
            DateFormat format = new SimpleDateFormat(dateFormat);
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
}
