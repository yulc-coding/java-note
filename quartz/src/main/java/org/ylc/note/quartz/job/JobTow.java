package org.ylc.note.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * TimeJob
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-08-27
 */
@Slf4j
public class JobTow extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("curTime: {}", LocalDateTime.now());
        log.info("desc: {}", context.getJobDetail().getDescription());
        log.info("key: {}", context.getJobDetail().getKey());
        // 获取 jobDataMap 中传入的数据
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        jobDataMap.forEach((k, v) -> log.info("{} : {}", k, v));
        log.info("=======================\r\n");
    }

}

