package com.scifi.adventure.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Player model representing the player state.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id; // database id, 0 if not persisted
    private String name;
    private int health;
    private List<String> inventory;
    private int currentNodeId;

    // --- New survival stats ---
    private int oxygen;
    private int score;
    private int threatLevel;
    public Player() {
        this.health = 100;
        this.inventory = new ArrayList<>();
        this.currentNodeId = 1;
        this.oxygen = 100;
        this.score = 0;
        this.threatLevel = 0;
    }


    public Player(String name, int health, List<String> inventory, int currentNodeId) {
        this.name = (name == null || name.isBlank()) ? "Unnamed" : name;
        this.health = Math.max(0, health);
        this.inventory = (inventory == null) ? new ArrayList<>() : new ArrayList<>(inventory);
        this.currentNodeId = Math.max(1, currentNodeId);
        this.oxygen = 100;
        this.score = 0;
        this.threatLevel = 0;
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("id must be non-negative");
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = (name == null || name.isBlank()) ? "Unnamed" : name.trim();
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = Math.max(0, health); }

    public List<String> getInventory() { return Collections.unmodifiableList(inventory); }
    public void setInventory(List<String> inventory) {
        this.inventory = (inventory == null) ? new ArrayList<>() : new ArrayList<>(inventory);
    }
    public void addToInventory(String item) {
        if (item != null && !item.isBlank()) this.inventory.add(item.trim());
    }
    public void removeFromInventory(String item) {
        if (item != null) this.inventory.remove(item);
    }

    public int getCurrentNodeId() { return currentNodeId; }
    public void setCurrentNodeId(int currentNodeId) { this.currentNodeId = Math.max(1, currentNodeId); }

    public boolean isAlive() { return this.health > 0; }

    public int getOxygen() { return oxygen; }
    public void setOxygen(int oxygen) { this.oxygen = Math.max(0, oxygen); }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = Math.max(0, score); }

    public int getThreatLevel() { return threatLevel; }
    public void setThreatLevel(int threatLevel) { this.threatLevel = Math.max(0, threatLevel); }
    
    

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", oxygen=" + oxygen +
                ", score=" + score +
                ", threatLevel=" + threatLevel +
                ", inventory=" + inventory +
                ", currentNodeId=" + currentNodeId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return id == player.id &&
                health == player.health &&
                oxygen == player.oxygen &&
                score == player.score &&
                threatLevel == player.threatLevel &&
                currentNodeId == player.currentNodeId &&
                Objects.equals(name, player.name) &&
                Objects.equals(inventory, player.inventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, health, oxygen, score, threatLevel, inventory, currentNodeId);
    }
}
