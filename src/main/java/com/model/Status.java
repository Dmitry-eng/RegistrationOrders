package com.model;


public class Status {
    private long id;
    private String description;

    public Status() {
    }

    public Status(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Status(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
