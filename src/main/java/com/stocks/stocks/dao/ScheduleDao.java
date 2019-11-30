package com.stocks.stocks.dao;

import com.stocks.stocks.model.Schedule;
import com.stocks.stocks.model.Stock;
import com.stocks.stocks.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository("scheduleDao")
public class ScheduleDao {

    private final MongoTemplate mongoTemplate;

    public ScheduleDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public String makeSchedule(Schedule schedule, String type, String userIdString) {
        if (schedule != null && (type.equals("buy") || type.equals("sell"))
                && schedule.getStockIds().size() > 0
                && schedule.getFreq() >= 0 && schedule.getFreq() <= 4
                && userIdString != null && ObjectId.isValid(userIdString)) {
            // ensure user is a real user
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);
            if (user == null)
                return "Cannot find user";

            // make sure start date is valid (not in the past)
            if (schedule.getStartDate().before(new Date()))
                return "Start date cannot be in the past";

            // set user as owner of the schedule
            schedule.setUserId(userId);

            // set type of schedule
            if (type.equals("buy")) {
                schedule.setBuy(true);

                // check if stocks requesting to be bought are real stocks
                for (ObjectId stockId : schedule.getStockIds()) {
                    if (mongoTemplate.findById(stockId, Stock.class) == null)
                        return "Cannot buy stocks that do not exist";
                }
            }
            else {
                schedule.setBuy(false);

                // check if stocks requesting to be sold are real owned stocks
                for (ObjectId stockId : schedule.getStockIds()) {
                    if (mongoTemplate.findById(stockId, Stock.class) == null)
                        return "Cannot sell stocks that do not exist";

                    if (user.indexOfStock(stockId) == -1)
                        return "Cannot sell stocks that are not owned";
                }
            }

            // save the schedule in the db
            mongoTemplate.save(schedule);
            return "Success!";
        }
        return "Invalid input";
    }

    public String editSchedule(Schedule newSchedule, String userIdString) {
        if (newSchedule != null && newSchedule.getStockIds().size() > 0
                && newSchedule.getFreq() >= 0 && newSchedule.getFreq() <= 4
                && userIdString != null && ObjectId.isValid(userIdString)) {
            // ensure user is a real user and owns schedule
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);
            if (user == null)
                return "Cannot find user";
            if (!newSchedule.getUserId().equals(userId))
                return "User must own schedule";

            // ensure schedule is real, that user is same owner, and that the type is the same
            Schedule oldSchedule = mongoTemplate.findById(newSchedule.getId(), Schedule.class);
            if (oldSchedule == null)
                return "Cannot find schedule";
            if (!oldSchedule.getUserId().equals(newSchedule.getUserId()))
                return "Owner cannot be edited";
            if (oldSchedule.isBuy() != newSchedule.isBuy())
                return "Type of schedule cannot be changed (buy/sell)";

            // make sure start date is valid (not in the past)
            if (newSchedule.getStartDate().before(new Date()))
                return "Start date cannot be in the past";

            // validate stocks
            if (newSchedule.isBuy()) {
                // check if stocks requesting to be bought are real stocks
                for (ObjectId stockId : newSchedule.getStockIds()) {
                    if (mongoTemplate.findById(stockId, Stock.class) == null)
                        return "Cannot buy stocks that do not exist";
                }
            }
            else {
                // check if stocks requesting to be sold are real owned stocks
                for (ObjectId stockId : newSchedule.getStockIds()) {
                    if (mongoTemplate.findById(stockId, Stock.class) == null)
                        return "Cannot sell stocks that do not exist";

                    if (user.indexOfStock(stockId) == -1)
                        return "Cannot sell stocks that are not owned";
                }
            }

            // update the schedule in the db
            mongoTemplate.save(newSchedule);
            return "Success!";
        }
        return "Invalid input";
    }

    public String deleteSchedule(String scheduleIdString, String userIdString) {
        if (scheduleIdString != null && ObjectId.isValid(scheduleIdString)
            && userIdString != null && ObjectId.isValid(userIdString)) {

            // ensure user is real
            ObjectId userId = new ObjectId(userIdString);
            User user = mongoTemplate.findById(userId, User.class);
            if (user == null)
                return "Cannot find user";

            // ensure schedule is real
            ObjectId scheduleId = new ObjectId(scheduleIdString);
            Schedule schedule = mongoTemplate.findById(scheduleId, Schedule.class);
            if (schedule == null)
                return "Cannot find schedule";

            // ensure user owns schedule
            if(!schedule.getUserId().equals(userId))
                return "User must own schedule";

            // remove the schedule in the db
            mongoTemplate.remove(schedule);
            return "Success!";
        }
        return "Invalid input";
    }
}
