package com.stocks.stocks.model;

public class Stock {
    private double price;
    private String company;
    private String tickerSymbol;
    private String releaseDate;

    public Stock(double price, String company, String tickerSymbol, String releaseDate) {
        this.price = price;
        this.company = company;
        this.tickerSymbol = tickerSymbol;
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
