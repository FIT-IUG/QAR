package com.example.abdalazez.qar.Model;

/**
 * Created by ABD ALAZEZ on 19/04/2018.
 */

public class User {

    public String from;
    public String type;
    public String date;
    public String content;

    public User() {
    }

    public User(String from, String type, String date) {
        this.from = from;
        this.type = type;
        this.date = date;
    }

    public User(String from, String type, String date, String content) {
        this.from = from;
        this.type = type;
        this.date = date;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
