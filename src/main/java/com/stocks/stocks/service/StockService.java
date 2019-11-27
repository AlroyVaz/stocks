package com.stocks.stocks.service;

import com.stocks.stocks.dao.StockDao;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
import com.stocks.stocks.model.StockWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class StockService {

    private final StockDao stockDao;

    @Autowired
    public StockService(@Qualifier("stockDao") StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public Stock getCurrentStockPriceFromTickerSymbol(String tickerSymbol, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getCurrentStockPriceFromTickerSymbol(tickerSymbol);
        return null;
    }

    public List<Stock> getCurrentStockPriceFromCompany(String company, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getCurrentStockPriceFromCompany(company);
        return null;
    }

    public StockPriceHistory getStockPriceHistoryFromTickerSymbol(String tickerSymbol, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getStockPriceHistoryFromTickerSymbol(tickerSymbol);
        return null;
    }

    public void buyStocks(List<Stock> stockList, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object username = session.getAttribute("USERNAME");
            if (username != null)
                stockDao.buyStocks(username.toString(), stockList);
        }
    }

    public void sellStocks(List<Stock> stockList, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            Object username = session.getAttribute("USERNAME");
            if (username != null)
                stockDao.sellStocks(username.toString(), stockList);
        }
    }
}
