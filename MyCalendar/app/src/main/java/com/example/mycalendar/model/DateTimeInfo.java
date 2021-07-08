package com.example.mycalendar.model;

public class DateTimeInfo {
    int dayOfMonthSolar,monthSolar,yearSolar;
    int dayOfMonthLunar,monthLunar,yearLunar;
    String strDayOfMonthLunar,strMonthLunar,strYearLunar;
    String dayOfWeek;
    String timeLunar;
    boolean isGoodTime;
    String isGoodDay;

    public boolean getIsGoodTime() {
        return isGoodTime;
    }

    public void setIsGoodTime(boolean goodTime) {
        isGoodTime = goodTime;
    }

    public String getIsGoodDay() {
        return isGoodDay;
    }

    public void setIsGoodDay(String isGoodDay) {
        this.isGoodDay = isGoodDay;
    }

    public int getDayOfMonthSolar() {
        return dayOfMonthSolar;
    }

    public void setDayOfMonthSolar(int dayOfMonthSolar) {
        this.dayOfMonthSolar = dayOfMonthSolar;
    }

    public int getMonthSolar() {
        return monthSolar;
    }

    public void setMonthSolar(int monthSolar) {
        this.monthSolar = monthSolar;
    }

    public int getYearSolar() {
        return yearSolar;
    }

    public void setYearSolar(int yearSolar) {
        this.yearSolar = yearSolar;
    }

    public int getDayOfMonthLunar() {
        return dayOfMonthLunar;
    }

    public void setDayOfMonthLunar(int dayOfMonthLunar) {
        this.dayOfMonthLunar = dayOfMonthLunar;
    }

    public int getMonthLunar() {
        return monthLunar;
    }

    public void setMonthLunar(int monthLunar) {
        this.monthLunar = monthLunar;
    }

    public int getYearLunar() {
        return yearLunar;
    }

    public void setYearLunar(int yearLunar) {
        this.yearLunar = yearLunar;
    }

    public String getStrDayOfMonthLunar() {
        return strDayOfMonthLunar;
    }

    public void setStrDayOfMonthLunar(String strDayOfMonthLunar) {
        this.strDayOfMonthLunar = strDayOfMonthLunar;
    }

    public String getStrMonthLunar() {
        return strMonthLunar;
    }

    public void setStrMonthLunar(String strMonthLunar) {
        this.strMonthLunar = strMonthLunar;
    }

    public String getStrYearLunar() {
        return strYearLunar;
    }

    public void setStrYearLunar(String strYearLunar) {
        this.strYearLunar = strYearLunar;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeLunar() {
        return timeLunar;
    }

    public void setTimeLunar(String timeLunar) {
        this.timeLunar = timeLunar;
    }
}
