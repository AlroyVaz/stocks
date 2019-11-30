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
    // freq=4 : recurring-weekly
    private int freq;

    // when to start buying/selling
    private Date startDate;

    // stocks to buy/sell
    private List<String> stockIds;

    // id of user that owns schedule
    private String userId;

    public Schedule(int freq, Date startDate, List<String> stockIds) {
        this.id = new ObjectId();
        this.buy = false;
        this.freq = freq;
        this.startDate = startDate;
        this.stockIds = stockIds;
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

    public List<String> getStockIds() {
        return stockIds;
    }

    public void setStockIds(List<String> stockIds) {
        this.stockIds = stockIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
