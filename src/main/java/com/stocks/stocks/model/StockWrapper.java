package com.stocks.stocks.model;

import java.util.List;

public class StockWrapper {
    private List<Stock> stockList;

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public StockWrapper(List<Stock> stockList) {
        this.stockList = stockList;
    }
}
