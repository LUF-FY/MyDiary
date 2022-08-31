package com.mugivara.mydiary;

import android.content.Context;
import android.icu.util.Calendar;
import android.text.format.DateUtils;


public class TextViewDate extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewDate(Context context) {
        super(context);
    }

    public void setTextDate(Context context, Calendar calendar){
        setText(DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

}
