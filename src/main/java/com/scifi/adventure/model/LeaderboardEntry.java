package com.scifi.adventure.model;

public class LeaderboardEntry {
    private String username;
    private int score;

    // Proper constructor
    public LeaderboardEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
