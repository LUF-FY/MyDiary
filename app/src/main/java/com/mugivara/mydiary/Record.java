package com.mugivara.mydiary;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

    private long id;
    private String title;
    private String text;
    private long date;

    public Record(long id, String title, String text, Date date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date.getTime();
    }

    public Record(long id, String title, String text, long date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
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

    public long getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public void setDate(long date) {
        this.date = date;
    }
}
