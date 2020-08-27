package org.ylc.note.quartz.config;

import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.ylc.note.quartz.job.TimeJob;

import java.time.LocalDateTime;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 定时任务配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-08-27
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail timeDetail() {
        return JobBuilder.newJob(TimeJob.class)
                .withIdentity("timeJob", "timeGroup")
                .storeDurably()
                // 在jobDataMap传入数据，可以在JOB中获取到
                .usingJobData("title", "this is a time job")
                .usingJobData("beginDate", LocalDateTime.now().toString())
                .build();
    }

    /**
     * quartz.trigger.simple 为true才执行
     */
    @Bean
    @ConditionalOnProperty(name = "simple-trigger")
    public Trigger simpleTrigger() {
        // 每隔5秒执行一次
        SimpleScheduleBuilder schedule = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(5)
                .repeatForever();
        return TriggerBuilder.newTrigger()
                .forJob(timeDetail())
                .withIdentity("timeJob", "timeGroup")
                .withDescription("simple trigger")
                .withSchedule(schedule)
                .build();
    }

    /**
     * quartz.trigger.cron 为true才执行
     */
    @Bean
    @ConditionalOnProperty(name = "cron-trigger")
    public Trigger cronTrigger() {
        // 每隔8秒执行一次
        CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule("*/8 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(timeDetail())
                .withIdentity("timeJob", "timeGroup")
                .withDescription("cron trigger")
                .withSchedule(schedule)
                .build();
    }

}
