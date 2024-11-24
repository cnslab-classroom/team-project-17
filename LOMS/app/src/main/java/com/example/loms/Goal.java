package com.example.loms;

import java.io.Serializable;
import java.util.UUID;

public class Goal implements Serializable {
    private String id;          // Unique ID
    private String title;       // Title of the goal
    private String description; // Description of the goal
    private String startDate;   // Start date
    private String endDate;     // End date
    private int currentWeek;    // Current week
    private int finalWeek;      // Final week
    private int progress;       // Progress percentage

    /**
     * Default constructor (required for Firebase).
     */
    public Goal() {
    }

    /**
     * Constructor with all fields.
     */
    public Goal(String id, String title, String description, String startDate, String endDate, int currentWeek, int finalWeek, int progress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentWeek = currentWeek;
        this.finalWeek = finalWeek;
        this.progress = progress;
    }

    /**
     * Constructor with ID auto-generation.
     */
    public Goal(String title, String description, int currentWeek, int finalWeek, int progress) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.currentWeek = currentWeek;
        this.finalWeek = finalWeek;
        this.progress = progress;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(int currentWeek) {
        if (currentWeek < 0 || currentWeek > finalWeek) {
            throw new IllegalArgumentException("Current week must be between 0 and final week.");
        }
        this.currentWeek = currentWeek;
    }

    public int getFinalWeek() {
        return finalWeek;
    }

    public void setFinalWeek(int finalWeek) {
        if (finalWeek <= 0) {
            throw new IllegalArgumentException("Final week must be greater than 0.");
        }
        this.finalWeek = finalWeek;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100.");
        }
        this.progress = progress;
    }

    /**
     * Checks if the goal is completed.
     *
     * @return True if progress is 100%, false otherwise.
     */
    public boolean isCompleted() {
        return progress == 100;
    }

    /**
     * Updates the progress of the goal.
     *
     * @param completedWeeks The number of weeks completed.
     */
    public void updateProgress(int completedWeeks) {
        if (completedWeeks < 0 || completedWeeks > finalWeek) {
            throw new IllegalArgumentException("Completed weeks must be between 0 and final week.");
        }
        this.progress = (int) (((double) completedWeeks / finalWeek) * 100);
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currentWeek=" + currentWeek +
                ", finalWeek=" + finalWeek +
                ", progress=" + progress +
                '}';
    }
}
