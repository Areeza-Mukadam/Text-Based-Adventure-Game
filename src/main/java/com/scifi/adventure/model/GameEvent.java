package com.scifi.adventure.model;

public class GameEvent {
    private String title;
    private String message;

    public GameEvent(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
}
