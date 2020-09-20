package org.ylc.note.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.ylc.note.quartz.constant.ConfigConstant;
import org.ylc.note.quartz.constant.EnumConstant;
import org.ylc.note.quartz.entity.ScheduleJob;
import org.ylc.note.quartz.entity.ScheduleJobLog;
import org.ylc.note.quartz.service.ScheduleJobLogService;
import org.ylc.note.quartz.util.ScheduleRunnable;
import org.ylc.note.quartz.util.SpringContextUtil;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
@Slf4j
public class QuartzScheduleJob extends QuartzJobBean {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        ScheduleJob job = (ScheduleJob) context.getMergedJobDataMap().get(ConfigConstant.JOB_PARAM_KEY);

        ScheduleJobLogService scheduleJobLogService = SpringContextUtil.getBean(ScheduleJobLogService.class);

        ScheduleJobLog jobLog = new ScheduleJobLog();
        jobLog.setJobId(job.getId());
        jobLog.setClassName(job.getClassName());
        jobLog.setMethodName(job.getMethodName());
        jobLog.setParameter(job.getParameter());
        jobLog.setBeginTime(LocalDateTime.now());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        log.info("准备执行任务，ID：[{}]", job.getId());

        try {
            ScheduleRunnable task = new ScheduleRunnable(job.getClassName(), job.getMethodName(), job.getParameter());

            Future<?> future = executorService.submit(task);
            future.get();

            jobLog.setExecutionStatus(EnumConstant.ExecutionStatus.SUCCESS.getCode());
        } catch (Exception e) {
            log.error("执行任务失败，id:[{}]", job.getId(), e);

            jobLog.setExecutionStatus(EnumConstant.ExecutionStatus.FAIL.getCode());
            jobLog.setErrMsg(e.getMessage());
        } finally {
            long endTime = System.currentTimeMillis();

            jobLog.setEndTime(LocalDateTime.now());
            jobLog.setExecutionTime(endTime - startTime);

            scheduleJobLogService.save(jobLog);
        }

    }
}
