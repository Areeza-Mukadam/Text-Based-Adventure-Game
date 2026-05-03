package com.scifi.adventure.model;

public class Achievement {
    private int id;
    private String name;
    private String description;
    private int xpValue;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getXpValue() { return xpValue; }
    public void setXpValue(int xpValue) { this.xpValue = xpValue; }
}
