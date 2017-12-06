package com.natateam.myevents.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macbook on 07/07/ 15.
 */
public class Event extends RealmObject {
    public static final String ID ="ID";
    @PrimaryKey
    private long id;
    private long dateMS;
    private String date;
    private String time;
    private String title;
    private String desc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateMS() {
        return dateMS;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDateMS(long dateMS) {
        this.dateMS = dateMS;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Event(long id) {
        this.id = id;
    }

    public Event() {
    }
}
