package com.mugivara.mydiary;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


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

    public static void setInitialDate(Context context, TextView tv, Calendar calendar){
        tv.setText(DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public static void setInitialDate(Context context, TextView tv, long date){
        tv.setText(DateUtils.formatDateTime(context,
                date,
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }
}
