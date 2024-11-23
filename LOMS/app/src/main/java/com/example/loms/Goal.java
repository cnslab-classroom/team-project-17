package com.example.loms;

import java.io.Serializable;
import java.util.UUID;

public class Goal implements Serializable {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private int currentWeek; // 현재 주차
    private int finalWeek;   // 최종 주차
    private int progress;    // 진행도 (%)

    public Goal() {
        // Default constructor for Firebase
    }

    // Constructor with all fields
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

    // Constructor for title, description, and week-related fields
    public Goal(String title, String description, int currentWeek, int finalWeek, int progress) {
        this.id = UUID.randomUUID().toString(); // Generate unique ID
        this.title = title;
        this.description = description;
        this.currentWeek = currentWeek;
        this.finalWeek = finalWeek;
        this.progress = progress;
        this.startDate = ""; // Default value for startDate
        this.endDate = "";   // Default value for endDate
    }

    // Getters and Setters
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
        this.currentWeek = currentWeek;
    }

    public int getFinalWeek() {
        return finalWeek;
    }

    public void setFinalWeek(int finalWeek) {
        this.finalWeek = finalWeek;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
