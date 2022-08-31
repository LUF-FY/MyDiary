package com.mugivara.mydiary;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Record implements Serializable {

    private long id;
    private String title;
    private String text;
    private Calendar calendar;


    public Record(long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.calendar = Calendar.getInstance();
    }

    public Record(long id, String title, String text, Date date) {
        setCalendarDate(date);
    }

    public Record(long id, String title, String text, long millis) {
        setCalendarMilic(millis);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        title = title;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getCalendar() {
        return calendar;
    }
    public long getCalendarInMillis() {
        return calendar.getTimeInMillis();
    }

    public void setCalendarDate(Date date) {
        this.calendar.setTime(date);
    }

    public void setCalendarMilic(long milic) {
        setCalendarDate(new Date(milic));
    }

}
