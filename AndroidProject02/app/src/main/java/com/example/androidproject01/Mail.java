package com.example.androidproject01;

import java.io.Serializable;

public class Mail implements Serializable {
    String sender;
    String recipient;
    String title;
    String content;
    String time;

    public Mail(String sender, String recipient, String title, String content, String time) {
        this.sender = sender;
        this.recipient = recipient;
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
