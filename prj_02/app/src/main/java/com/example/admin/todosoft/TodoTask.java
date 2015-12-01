package com.example.admin.todosoft;

/**
 * Created by admin on 23.11.2015.
 */
public class TodoTask {

    long id, status = 0;
    String description, date;

    public TodoTask(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public TodoTask(String date, String description, long id) {
        this.date = date;
        this.description = description;
        this.id = id;
    }

/*
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status ) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
*/
}
