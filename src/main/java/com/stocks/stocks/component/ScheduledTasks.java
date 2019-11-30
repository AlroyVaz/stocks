package com.stocks.stocks.component;

import com.stocks.stocks.dao.StockDao;
import com.stocks.stocks.model.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScheduledTasks {
    private final MongoTemplate mongoTemplate;
    private final StockDao stockDao;
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    public ScheduledTasks(MongoTemplate mongoTemplate, StockDao stockDao) {
        this.mongoTemplate = mongoTemplate;
        this.stockDao = stockDao;
    }

    @Scheduled(fixedRate = 100)
    public void manageSchedules() {
        Query query = new Query();
        query.with(new Sort(Sort.Direction.ASC, "startDate"));
        List<Schedule> allSchedules = mongoTemplate.findAll(Schedule.class);
        if (allSchedules.size() == 0)
            return;
        Schedule currentSchedule = allSchedules.get(0);
        Date now = new Date();
        if (currentSchedule.getStartDate().equals(now) || currentSchedule.getStartDate().before(now)) {
            log.info("Now it is: " + now);
            log.info("      Schedule: " + currentSchedule.getStartDate());
            boolean success;
            if (currentSchedule.isBuy())
                success = stockDao.buyStocksNow(currentSchedule.getStockIds(), currentSchedule.getUserId());
            else
                success = stockDao.sellStocksNow(currentSchedule.getStockIds(), currentSchedule.getUserId());

            if (success && currentSchedule.getFreq() > 1) {
                Calendar cal = Calendar.getInstance();
                Date prevDate = currentSchedule.getStartDate();
                cal.setTime(prevDate);
                log.info("Successful (recurring) buy/sell!");
                switch (currentSchedule.getFreq()) {
                    case 2:
                        cal.add(Calendar.HOUR_OF_DAY, 1);
                        break;
                    case 3:
                        cal.add(Calendar.DATE, 1);
                        break;
                    case 4:
                        cal.add(Calendar.DATE, 7);
                        break;
                }
                while (!stockDao.validateTime(cal.getTime()))
                    cal.add(Calendar.DATE, 1);

                currentSchedule.setStartDate(cal.getTime());
                mongoTemplate.save(currentSchedule);
            }
            // One-time
            else if (currentSchedule.getFreq() == 0) {
                log.info("Successful one-time buy/sell!");
                mongoTemplate.remove(currentSchedule);
            }
            // Every minute
            else if (currentSchedule.getFreq() == 1) {
                log.info("Successful (recurring) buy/sell!");
                Date prevDate = currentSchedule.getStartDate();
                currentSchedule.setStartDate(new Date(prevDate.getTime() + 60000));
                mongoTemplate.save(currentSchedule);
            }
            else {
                log.info("FAIL! Removing schedule...");
                mongoTemplate.remove(currentSchedule);
            }
        }
    }
}
