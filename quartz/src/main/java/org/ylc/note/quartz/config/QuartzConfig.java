package org.ylc.note.quartz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

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
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        Properties quartzProperties = new Properties();
        quartzProperties.put("org.quartz.scheduler.instanceName", "MyScheduler");
        quartzProperties.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        quartzProperties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        quartzProperties.put("org.quartz.threadPool.threadCount", "20");
        quartzProperties.put("org.quartz.threadPool.threadPriority", "5");
        //JobStore配置（存放在内存中）
        quartzProperties.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        quartzProperties.put("org.quartz.jobStore.misfireThreshold", "120000");

        factory.setQuartzProperties(quartzProperties);
        factory.setSchedulerName("MyScheduler");
        // 延迟启动，单位秒
        factory.setStartupDelay(30);
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factory.setAutoStartup(true);

        return factory;
    }

    // @Bean
    // public JobDetail timeDetail() {
    //     return JobBuilder.newJob(TimeJob.class)
    //             .withIdentity("timeJob", "timeGroup")
    //             .storeDurably()
    //             // 在jobDataMap传入数据，可以在JOB中获取到
    //             .usingJobData("title", "this is a time job")
    //             .usingJobData("beginDate", LocalDateTime.now().toString())
    //             .build();
    // }
    //
    // @Bean
    // public Trigger simpleTrigger() {
    //     // 每隔5秒执行一次
    //     SimpleScheduleBuilder schedule = SimpleScheduleBuilder
    //             .simpleSchedule()
    //             .withIntervalInSeconds(5)
    //             .repeatForever();
    //     return TriggerBuilder.newTrigger()
    //             .forJob(timeDetail())
    //             .withIdentity("timeJob", "timeGroup")
    //             .withDescription("simple trigger")
    //             .withSchedule(schedule)
    //             .build();
    // }
    //
    // @Bean
    // public Trigger cronTrigger() {
    //     // 每隔8秒执行一次
    //     CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule("*/8 * * * * ?");
    //     return TriggerBuilder.newTrigger()
    //             .forJob(timeDetail())
    //             .withIdentity("timeJob", "timeGroup")
    //             .withDescription("cron trigger")
    //             .withSchedule(schedule)
    //             .build();
    // }

}
