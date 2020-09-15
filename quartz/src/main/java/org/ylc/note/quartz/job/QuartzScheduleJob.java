package org.ylc.note.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.ylc.note.quartz.entity.ScheduleJob;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 执行定时任务，记录日志
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-13
 */
public class QuartzScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("JOB_PARAM_KEY");
    }
}
