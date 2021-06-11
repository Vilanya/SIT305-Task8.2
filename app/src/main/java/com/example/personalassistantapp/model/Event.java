package com.example.personalassistantapp.model;

public class Event {
    private int id;
    private String name, date, time;
    private String status;

    public Event(String name, String date, String time,String status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}

