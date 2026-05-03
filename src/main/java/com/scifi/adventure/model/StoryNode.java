package com.scifi.adventure.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * StoryNode represents a point in the story graph.
 */
public class StoryNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String description;
    private String option1Text;
    private int option1Next;
    private String option2Text;
    private int option2Next;

    // --- New fields for advanced gameplay ---
    private boolean hackingNode;
    private String hackingAnswer;
    private String requiredItem;

    public StoryNode() {}

    public StoryNode(int id, String description, String option1Text, int option1Next,
                     String option2Text, int option2Next) {
        this.id = id;
        this.description = description;
        this.option1Text = option1Text;
        this.option1Next = option1Next;
        this.option2Text = option2Text;
        this.option2Next = option2Next;
        this.hackingNode = false;
        this.hackingAnswer = null;
        this.requiredItem = null;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        this.id = id;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = (description == null) ? "" : description.trim();
    }

    public String getOption1Text() { return option1Text; }
    public void setOption1Text(String option1Text) {
        this.option1Text = (option1Text == null) ? "" : option1Text.trim();
    }

    public int getOption1Next() { return option1Next; }
    public void setOption1Next(int option1Next) { this.option1Next = option1Next; }

    public String getOption2Text() { return option2Text; }
    public void setOption2Text(String option2Text) {
        this.option2Text = (option2Text == null) ? "" : option2Text.trim();
    }

    public int getOption2Next() { return option2Next; }
    public void setOption2Next(int option2Next) { this.option2Next = option2Next; }

    public boolean isHackingNode() { return hackingNode; }
    public void setHackingNode(boolean hackingNode) { this.hackingNode = hackingNode; }

    public String getHackingAnswer() { return hackingAnswer; }
    public void setHackingAnswer(String hackingAnswer) { this.hackingAnswer = hackingAnswer; }

    public String getRequiredItem() { return requiredItem; }
    public void setRequiredItem(String requiredItem) { this.requiredItem = requiredItem; }

    @Override
    public String toString() {
        return "StoryNode{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", option1Text='" + option1Text + '\'' +
                ", option1Next=" + option1Next +
                ", option2Text='" + option2Text + '\'' +
                ", option2Next=" + option2Next +
                ", hackingNode=" + hackingNode +
                ", hackingAnswer='" + hackingAnswer + '\'' +
                ", requiredItem='" + requiredItem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoryNode)) return false;
        StoryNode storyNode = (StoryNode) o;
        return id == storyNode.id &&
                option1Next == storyNode.option1Next &&
                option2Next == storyNode.option2Next &&
                hackingNode == storyNode.hackingNode &&
                Objects.equals(description, storyNode.description) &&
                Objects.equals(option1Text, storyNode.option1Text) &&
                Objects.equals(option2Text, storyNode.option2Text) &&
                Objects.equals(hackingAnswer, storyNode.hackingAnswer) &&
                Objects.equals(requiredItem, storyNode.requiredItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, option1Text, option1Next,
                            option2Text, option2Next, hackingNode,
                            hackingAnswer, requiredItem);
    }
}
