package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String content;
    private String date;
    private String sentby;
    public String imageurl;

    public Message(String content, String date, String sentby, String imageurl) {
        this.content = content;
        this.date = date;
        this.sentby = sentby;
        this.imageurl = imageurl;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSentby() {
        return sentby;
    }

    public void setSentby(String sentby) {
        this.sentby = sentby;
    }

    public void setImageurl(String imageurl){
        this.imageurl = imageurl;
    }

    public String getImageurl(){
        return imageurl;
    }
}
