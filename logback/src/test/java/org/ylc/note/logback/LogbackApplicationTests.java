package org.ylc.note.logback;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ylc.note.logback.service.MyService;

@SpringBootTest
class LogbackApplicationTests {

    /**
     * 普通类日志
     */
    private final Logger normalLogger = LoggerFactory.getLogger(LogbackApplicationTests.class);

    /**
     * 定时任务类日志，这里的name必须和XML中logger 的name一致，这里为 schedule
     */
    private final Logger scheduleLogger = LoggerFactory.getLogger("schedule");

    @Autowired
    private MyService myService;


    /**
     * 普通日志测试
     */
    @Test
    void normalLogTest() {
        normalLogger.info("this is a info log with out args");
        normalLogger.error("this is a error log with out args");
    }


    /**
     * 带参数的日志测试
     */
    @Test
    void argsLogTest() {
        MDC.put("user", "鱼大仙");
        MDC.put("args", "参数");
        normalLogger.info("this is a info log with args");
        normalLogger.error("this is a error log with args");
    }


    /**
     * 独立日志测试
     * 1、指定日志名称
     * 2、指定包或者类
     */
    @Test
    void independentLogTest() {
        // 指定名称
        scheduleLogger.info("this is a schedule info log");
        scheduleLogger.error("this is a schedule error log");
        // 指定包或者类
        myService.myLog();
    }


}
