package com.stocks.stocks.dao;

import com.stocks.stocks.model.Schedule;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.StockPriceHistory;
import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository("stockDao")
public class StockDao {
    private final MongoTemplate mongoTemplate;
    private final ScheduleDao scheduleDao;

    public StockDao(MongoTemplate mongoTemplate, ScheduleDao scheduleDao) {
        this.mongoTemplate = mongoTemplate;
        this.scheduleDao = scheduleDao;
    }

    // THIS PART NEEDS TO BE CHANGED!!!!!!!!!!!!!!!!!!!!!
    // update price and return the new price
    private double updateStockPrice(Stock stock) {
        // get current price from other server
        double newPrice = stock.getPrice() + 1;

        // update price in db if necessary
        if (newPrice != stock.getPrice()) {
            stock.setPrice(newPrice);
            mongoTemplate.save(stock);
        }

        // return price
        return newPrice;
    }

    // return list of stocks matching the search criteria or null if there is an error in the input
    public List<Stock> getStocks(String property, String value) {
        if (value != null && (property.equals("tickerSymbol") || property.equals("company") || property.equals("name"))) {
            // search for stocks
            Query query = new Query();
            query.addCriteria(Criteria.where(property).is(value));
            List<Stock> stocks = mongoTemplate.find(query, Stock.class);

            // update the prices of all stocks
            for (Stock s : stocks)
                updateStockPrice(s);

            // return stocks found
            return stocks;
        }
        return null;
    }

    // return current stock price or -1 if the stock is not found or for invalid inputs
    public double getCurrentStockPrice(String stockIdString) {
        if (stockIdString != null && ObjectId.isValid(stockIdString)) {
            ObjectId stockId = new ObjectId(stockIdString);

            // make sure stock exists in db
            Stock stock = mongoTemplate.findById(stockId, Stock.class);
            if (stock == null)
                return -1;

            // get updated stock price
            return updateStockPrice(stock);
        }
        return -1;
    }

    // return stock price history for chosen stock or null if the stock is not found or for invalid inputs
    public StockPriceHistory getStockPriceHistory(String stockIdString) {
        if (stockIdString != null && ObjectId.isValid(stockIdString)) {
            ObjectId stockId = new ObjectId(stockIdString);
            // make sure stock exists in db
            Stock stock = mongoTemplate.findById(stockId, Stock.class);
            if (stock == null)
                return null;

            // THIS PART NEEDS TO BE CHANGED!!!!!!!!!!!!!!!!!!!!!
            // get stock price history from other server
            double lastBusinessDay = updateStockPrice(stock);
            double currentWeek = 0;
            double pastWeek = 0;
            double monthToDate = 0;
            double yearToDate = 0;
            double past5Years = 0;

            // return stock price history
            return new StockPriceHistory(lastBusinessDay, currentWeek, pastWeek, monthToDate, yearToDate, past5Years);
        }
        return null;
    }

    public boolean validateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return hour >= 10 && hour <= 17 && day > 1 && day < 7;
    }

    public boolean buyStocks(List<String> stockIdStringList, String userIdString) {
        if (stockIdStringList != null && userIdString != null
                && stockIdStringList.size() > 0
                && ObjectId.isValid(userIdString)) {
            Schedule newSchedule = new Schedule(0, new Date(), stockIdStringList);
            scheduleDao.makeSchedule(newSchedule, "buy", userIdString);
            return true;
        }
        return false;
    }

    public void sellStocks(List<String> stockIdStringList, String userIdString) {
        if (stockIdStringList != null && userIdString != null
                && stockIdStringList.size() > 0
                && ObjectId.isValid(userIdString)) {
            Schedule newSchedule = new Schedule(0, new Date(), stockIdStringList);
            scheduleDao.makeSchedule(newSchedule, "sell", userIdString);
        }
    }

    public boolean buyStocksNow(List<String> stockIdStringList, String userIdString) {
        if (stockIdStringList != null && userIdString != null
                && stockIdStringList.size() > 0
                && ObjectId.isValid(userIdString)
                && validateTime(new Date())) {
            // get user from db
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);
            if (user == null)
                return false;

            for (String stockIdString : stockIdStringList) {
                if (stockIdString !=  null && ObjectId.isValid(stockIdString)) {
                    ObjectId stockId = new ObjectId(stockIdString);
                    Stock stock = mongoTemplate.findById(stockId, Stock.class);
                    if (stock == null)
                        return false;

                    // ensure that user has enough money in their balance
                    stock.setPrice(updateStockPrice(stock));
                    if (user.getBalance() < stock.getPrice())
                        return false;

                    // reduce user 'balance' by stock price
                    user.setBalance(user.getBalance() - stock.getPrice());

                    // add chosen stock to list of user's stocks
                    user.addStock(stockIdString);
                }
            }
            // update user in the db
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public boolean sellStocksNow(List<String> stockIdStringList, String userIdString) {
        if (stockIdStringList != null && userIdString != null
                && stockIdStringList.size() > 0
                && ObjectId.isValid(userIdString)
                && validateTime(new Date())) {
            // get user from db
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);
            if (user == null)
                return false;

            for (String stockIdString : stockIdStringList) {
                if (stockIdString != null && ObjectId.isValid(stockIdString)) {
                    // check if user actually has this stock
                    if (user.indexOfStock(stockIdString) == -1)
                        return false;

                    // get stock from db
                    ObjectId stockId = new ObjectId(stockIdString);
                    Stock stock = mongoTemplate.findById(stockId, Stock.class);
                    if (stock == null)
                        return false;

                    // add stock's price to user's balance
                    stock.setPrice(updateStockPrice(stock));
                    user.setBalance(user.getBalance() + stock.getPrice());

                    // remove chosen stock from user's stocks
                    user.removeStock(stockIdString);
                }
            }
            // update user in the db
            mongoTemplate.save(user);
            return true;
        }
        return false;
    }

    public List<Stock> getAllStocks() {
        return mongoTemplate.findAll(Stock.class);
    }
}
