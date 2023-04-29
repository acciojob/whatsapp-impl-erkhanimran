package com.driver;

import java.time.LocalDate;

public class Message {
    private int id;
    private String content;
    private LocalDate timestamp;

    public Message(int id, String content) {
        this.id = id;
        this.content = content;
        this.timestamp = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
