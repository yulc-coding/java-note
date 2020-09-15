package org.ylc.note.quartz.service;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class ScheduleJobService {

    private final ScheduleJobRepository scheduleJobRepository;

    private final QuartzService quartzService;

    public ScheduleJobService(ScheduleJobRepository scheduleJobRepository, QuartzService quartzService) {
        this.scheduleJobRepository = scheduleJobRepository;
        this.quartzService = quartzService;
    }

    public List<ScheduleJob> list() {
        return scheduleJobRepository.findAll();
    }

    /**
     * 创建定时任务
     * 1、数据库新增
     * 2、构建任务
     */
    public void createJob(ScheduleJob job) {
        job.setStatus(EnumConstant.JobStatus.NORMAL.getCode());
        job.setCreateTime(LocalDateTime.now());
        scheduleJobRepository.save(job);

        quartzService.createJob(job);
    }

    /**
     * 修改定时任务
     * 1、更新数据库信息
     * 2、如果修改了Cron表达式的，需要重置定时任务执行信息
     */
    public void modifyJob(ScheduleJob job) {
        ScheduleJob oldJob = scheduleJobRepository.findById(job.getId()).orElse(null);
        if (oldJob == null) {
            log.error("无效的定时任务");
            return;
        }
        scheduleJobRepository.save(job);

        if (!oldJob.getCronExpression().equals(job.getCronExpression())) {
            quartzService.modifyJob(job);
        }
    }

    public void pauseJob(Long jobId) {
        ScheduleJob job = new ScheduleJob();
        job.setId(jobId);
        job.setStatus(EnumConstant.JobStatus.PAUSED.getCode());
        scheduleJobRepository.save(job);

        quartzService.pauseJob(jobId);
    }

    public void resumeJob(long jobId) {
        ScheduleJob job = new ScheduleJob();
        job.setId(jobId);
        job.setStatus(EnumConstant.JobStatus.NORMAL.getCode());
        scheduleJobRepository.save(job);

        quartzService.resumeJob(jobId);
    }

    public void removeJob(long jobId) {
        scheduleJobRepository.deleteById(jobId);

        quartzService.removeJob(jobId);
    }
}
