package com.example.mycalendar.model;

import java.util.Date;

public class Zodiac {
    public Date getDateRange() {
        return dateRange;
    }

    public void setDateRange(Date dateRange) {
        this.dateRange = dateRange;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getSuitColor() {
        return suitColor;
    }

    public void setSuitColor(String suitColor) {
        this.suitColor = suitColor;
    }

    public int getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(int luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public String getLuckyTime() {
        return luckyTime;
    }

    public void setLuckyTime(String luckyTime) {
        this.luckyTime = luckyTime;
    }

    public Date dateRange;
    public Date currentDate;
    public String description;
    public String compatibility;
    public String mood;
    public String suitColor;
    public int luckyNumber;
    public String luckyTime;

}
