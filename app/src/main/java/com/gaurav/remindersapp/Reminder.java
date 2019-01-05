package com.gaurav.remindersapp;

import java.io.Serializable;

public class Reminder implements Serializable {
    Long time;
    String description;

    public Reminder(Long time, String description) {
        this.time = time;
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
