package com.stocks.stocks.api;

import com.stocks.stocks.model.Schedule;
import com.stocks.stocks.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("schedule")
@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/create/{type}")
    public void makeSchedule(@RequestBody Schedule schedule, @PathVariable("type") String type, HttpServletRequest request) {
        scheduleService.makeSchedule(schedule, type, request);
    }

    @PostMapping("/edit")
    public void editSchedule(@RequestBody Schedule schedule, HttpServletRequest request) {
        scheduleService.editSchedule(schedule, request);
    }

    @PostMapping("/delete")
    public void deleteSchedule(@RequestBody String scheduleId, HttpServletRequest request) {
        scheduleService.deleteSchedule(scheduleId, request);
    }
}
