package com.stocks.stocks.dao;

import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("stockDao")
public class StockDao {
    public Stock getCurrentStockPriceFromTickerSymbol(String tickerSymbol) {
        // get current stock price for chosen stock
        return new Stock(0.02, "Company1", tickerSymbol, "9999-99-99");
    }

    public List<Stock> getCurrentStockPriceFromCompany(String company) {
        // get current stocks and prices for chosen company
        List<Stock> list = new ArrayList<Stock>();
        list.add(new Stock(0.02, company, "AAA", "9999-99-99"));
        list.add(new Stock(0.01, company, "BBB", "1111-11-11"));
        return list;
    }

    public StockPriceHistory getStockPriceHistoryFromTickerSymbol(String tickerSymbol) {
        // get stock price history for chosen stock
        return new StockPriceHistory(0.01, 0, 2, 1, 3, 1);
    }

    public void buyStocks(String username, List<Stock> stockList) {
        // add chosen stock to list of user's stocks
        // ensure that user 'balance' >= stock.price
        // reduce user 'balance' by stock.price
    }

    public void sellStocks(String username, List<Stock> stockList) {
        // remove chosen stock from user's stocks
        // add stock.price to user 'balance'
    }
}
