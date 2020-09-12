package org.ylc.note.quartz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ylc.note.quartz.constant.EnumConstant;
import org.ylc.note.quartz.entity.ScheduleJob;
import org.ylc.note.quartz.repository.ScheduleJobRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-11
 */
@Service
public class ScheduleJobService {

    @Autowired
    private ScheduleJobRepository scheduleJobRepository;

    @Autowired
    private QuartzService quartzService;

    public List<ScheduleJob> list() {
        return scheduleJobRepository.findAll();
    }

    public void createJob(ScheduleJob job) {
        job.setStatus(EnumConstant.JobStatus.NORMAL.getCode());
        job.setCreateTime(LocalDateTime.now());
        scheduleJobRepository.save(job);
        quartzService.createJob();
    }

    public void pauseJob() {
        quartzService.pauseJob();
    }

    public void restoreJob() {
        quartzService.restoreJob();
    }

    public void modifyJob() {
        quartzService.modifyJob();
    }

    public void removeJob() {
        quartzService.restoreJob();
    }
}
