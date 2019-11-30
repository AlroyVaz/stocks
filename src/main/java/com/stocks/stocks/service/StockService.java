package com.stocks.stocks.service;

import com.stocks.stocks.dao.StockDao;
import com.stocks.stocks.model.Schedule;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
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

    public List<Stock> getStocks(String property, String value, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getStocks(property, value);
        return null;
    }

    public double getCurrentStockPrice(String stockId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getCurrentStockPrice(stockId);
        return -1;
    }

    public StockPriceHistory getStockPriceHistory(String stockId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getStockPriceHistory(stockId);
        return null;
    }

    public void buyStocks(List<String> stockIdList, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                stockDao.buyStocks(stockIdList, userId);
        }
    }

    public void sellStocks(List<String> stockIdList, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                stockDao.sellStocks(stockIdList, userId);
        }
    }

    public List<Stock> getAllStocks(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("USERNAME") != null)
            return stockDao.getAllStocks();
        return null;
    }
}
