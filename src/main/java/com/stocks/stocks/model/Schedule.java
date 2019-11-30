package com.stocks.stocks.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "ScheduleCollection")
public class Schedule {
    @Id
    private ObjectId id;

    // buy=true  : buy
    // buy=false : sell
    private boolean buy;

    // freq=0 : one-time
    // freq=1 : recurring-every minute
    // freq=2 : recurring-every hour
    // freq=3 : recurring-daily
    // freq=4 : recurring-monthly
    private int freq;

    // when to start buying/selling
    private Date startDate;

    // stocks to buy/sell
    private List<ObjectId> stockIds;

    // id of user that owns schedule
    private ObjectId userId;

    public Schedule(int freq, Date startDate, List<ObjectId> stocks) {
        this.id = new ObjectId();
        this.buy = false;
        this.freq = freq;
        this.startDate = startDate;
        this.stockIds = stocks;
        this.userId = null;
    }

    public ObjectId getId() {
        return id;
    }

    public boolean isBuy() {
        return buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<ObjectId> getStockIds() {
        return stockIds;
    }

    public void setStockIds(List<ObjectId> stockIds) {
        this.stockIds = stockIds;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }
}
