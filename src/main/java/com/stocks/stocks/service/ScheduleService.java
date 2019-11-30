package com.stocks.stocks.service;

import com.stocks.stocks.dao.ScheduleDao;
import com.stocks.stocks.model.Schedule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;

    public ScheduleService(@Qualifier("scheduleDao") ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    public void makeSchedule(Schedule schedule, String type, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                scheduleDao.makeSchedule(schedule, type, userId);
        }
    }

    public void editSchedule(Schedule schedule, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                scheduleDao.editSchedule(schedule, userId);
        }
    }

    public void deleteSchedule(String scheduleId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String userId = (String)session.getAttribute("USER_ID");
            if (userId != null)
                scheduleDao.deleteSchedule(scheduleId, userId);
        }
    }
}
