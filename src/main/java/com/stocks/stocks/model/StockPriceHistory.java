package com.stocks.stocks.model;

public class StockPriceHistory {
    private double lastBusinessDay;
    private double currentWeek;
    private double pastWeek;
    private double monthToDate;
    private double yearToDate;
    private double past5Years;

    public StockPriceHistory(double lastBusinessDay, double currentWeek, double pastWeek, double monthToDate, double yearToDate, double past5Years) {
        this.lastBusinessDay = lastBusinessDay;
        this.currentWeek = currentWeek;
        this.pastWeek = pastWeek;
        this.monthToDate = monthToDate;
        this.yearToDate = yearToDate;
        this.past5Years = past5Years;
    }

    public double getLastBusinessDay() {
        return lastBusinessDay;
    }

    public void setLastBusinessDay(double lastBusinessDay) {
        this.lastBusinessDay = lastBusinessDay;
    }

    public double getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(double currentWeek) {
        this.currentWeek = currentWeek;
    }

    public double getPastWeek() {
        return pastWeek;
    }

    public void setPastWeek(double pastWeek) {
        this.pastWeek = pastWeek;
    }

    public double getMonthToDate() {
        return monthToDate;
    }

    public void setMonthToDate(double monthToDate) {
        this.monthToDate = monthToDate;
    }

    public double getYearToDate() {
        return yearToDate;
    }

    public void setYearToDate(double yearToDate) {
        this.yearToDate = yearToDate;
    }

    public double getPast5Years() {
        return past5Years;
    }

    public void setPast5Years(double past5Years) {
        this.past5Years = past5Years;
    }
}
