package com.example.loms;

import java.io.Serializable;
import java.util.UUID;

public class Goal implements Serializable {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;

    public Goal() {
        // Default constructor for Firebase
    }

    public Goal(String id, String title, String description, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // New constructor for title and description only
    public Goal(String title, String description) {
        this.id = UUID.randomUUID().toString(); // Generate unique ID
        this.title = title;
        this.description = description;
        this.startDate = ""; // Default value for startDate
        this.endDate = "";   // Default value for endDate
    }

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
}
