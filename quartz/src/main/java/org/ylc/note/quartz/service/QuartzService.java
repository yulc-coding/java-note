package org.ylc.note.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.ylc.note.quartz.constant.ConfigConstant;
import org.ylc.note.quartz.constant.EnumConstant;
import org.ylc.note.quartz.entity.ScheduleJob;
import org.ylc.note.quartz.job.QuartzScheduleJob;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-11
 */
@Slf4j
@Component
public class QuartzService {

    private final Scheduler scheduler;

    private final static String JOB_NAME = "TASK_";

    public QuartzService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取触发器
     */
    public CronTrigger getCronTrigger(Long jobId) throws SchedulerException {
        return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
    }

    /**
     * 创建定时任务
     */
    public void createJob(ScheduleJob job) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(QuartzScheduleJob.class).withIdentity(getJobKey(job.getId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getId())).withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ConfigConstant.JOB_PARAM_KEY, job);
            // 生成定时任务
            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (job.getStatus().equals(EnumConstant.JobStatus.PAUSED.getValue())) {
                pauseJob(job.getId());
            }
        } catch (SchedulerException e) {
            log.error("创建定时任务失败：{}", e.getMessage());
        }
    }

    /**
     * 暂停定时任务
     *
     * @param jobId 指定的任务
     */
    public void pauseJob(long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("暂停任务失败:{}", e.getMessage());
        }
    }

    /**
     * 恢复定时任务
     *
     * @param jobId 指定的任务
     */
    public void resumeJob(long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("恢复任务失败:{}", e.getMessage());
        }
    }

    /**
     * 修改定时任务
     *
     * @param job 修改后的任务信息
     */
    public void modifyJob(ScheduleJob job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(job.getId());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //参数
            trigger.getJobDataMap().put(ConfigConstant.JOB_PARAM_KEY, job);
            // 重置定时任务
            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (job.getStatus().equals(EnumConstant.JobStatus.PAUSED.getValue())) {
                pauseJob(job.getId());
            }
        } catch (SchedulerException e) {
            log.error("更新定时任务失败:{}", e.getMessage());
        }
    }

    /**
     * 移除定时任务
     *
     * @param jobId 指定的任务
     */
    public void removeJob(long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除任务失败:{}", e.getMessage());
        }
    }

    /**
     * 立即执行指定的定时任务
     *
     * @param job 任务信息
     */
    public void run(ScheduleJob job) {

    }


}
