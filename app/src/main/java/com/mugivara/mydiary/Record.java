package com.mugivara.mydiary;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Record implements Serializable {

    private long id;
    private String title;
    private String text;
    private Date date;


    public Record(long id, String title, String text) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = new Date();
    }

    public Record(long id, String title, String text, Date date) {
        this(id,title,text);
        setDate(date);
    }

    public Record(long id, String title, String text, long millis) {
        this(id,title,text);
        setDateMillis(millis);
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
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    public long getDateInMillis() {
        return date.getTime();
    }

    public void setDate(Date date) {
        this.date = new Date();
    }

    public void setDateMillis(long millis) {
        this.date = new Date(millis);
    }

    @Override
    public String toString() {
        return title + '\n' + date + '\n' + text + '\n';
    }
}
