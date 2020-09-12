package org.ylc.note.quartz.controller;

import org.springframework.web.bind.annotation.*;
import org.ylc.note.quartz.entity.ScheduleJob;
import org.ylc.note.quartz.service.ScheduleJobService;

import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-11
 */
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    private final ScheduleJobService scheduleJobService;

    public ScheduleController(ScheduleJobService scheduleJobService) {
        this.scheduleJobService = scheduleJobService;
    }

    @GetMapping("/list")
    public List<ScheduleJob> list() {
        List<ScheduleJob> jobs = scheduleJobService.list();
        jobs.forEach(System.out::print);
        return jobs;
    }

    @PostMapping("/create")
    public void create(@RequestBody ScheduleJob job) {
        scheduleJobService.createJob(job);
    }

}
