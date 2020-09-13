package org.ylc.note.quartz.boot;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.ylc.note.quartz.entity.ScheduleJob;
import org.ylc.note.quartz.service.QuartzService;
import org.ylc.note.quartz.service.ScheduleJobService;

import java.util.List;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 项目启动后初始化定时任务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-13
 */
@Slf4j
@Component
@Order(2)
public class ScheduleJobStartupBoot implements CommandLineRunner {

    @Autowired
    private QuartzService quartzService;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Override
    public void run(String... args) {
        List<ScheduleJob> jobs = scheduleJobService.list();

        for (ScheduleJob job : jobs) {

            CronTrigger cronTrigger;
            try {
                cronTrigger = quartzService.getCronTrigger(job.getId());
            } catch (SchedulerException e) {
                log.error("获取触发器失败,JOB:【{}】,{}", job.getId(), e.getMessage());
                continue;
            }
            // 如果不存在，则创建
            if (cronTrigger == null) {
                quartzService.createJob(job);
            } else {
                quartzService.modifyJob(job);
            }

        }
    }
}
