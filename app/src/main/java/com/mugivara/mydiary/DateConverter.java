package com.mugivara.mydiary;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class DateConverter {

    public static void setInitialDate(Context context, TextView tv, Calendar calendar){
        tv.setText(DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    public static void setInitialDate(Context context, TextView tv, long Millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Millis);

        tv.setText(DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

}
