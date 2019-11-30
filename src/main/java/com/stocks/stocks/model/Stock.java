package com.stocks.stocks.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "StockCollection")
public class Stock {
    @Id
    private ObjectId id;
    private String name;
    private double price;
    private String company;
    private String tickerSymbol;

    public Stock(double price, String company, String tickerSymbol, String name) {
        this.id = new ObjectId();
        this.name = name;
        this.price = price;
        this.company = company;
        this.tickerSymbol = tickerSymbol;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
