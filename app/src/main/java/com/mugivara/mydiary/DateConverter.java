package com.mugivara.mydiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    static {
        dateFormat.setLenient(false);
    }

    public static String fromMilicToDate(long milic){
        Date date = new Date(milic);
        return dateFormat.format(date);
    }

    public static long fromDateToMilic(String dateString){
        try {
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
