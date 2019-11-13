package org.ylc.note.log4j2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ylc.note.log4j2.service.MyService;

@SpringBootTest
class Log4j2ApplicationTests {

    /**
     * 普通类日志
     */
    private final Logger normalLogger = LoggerFactory.getLogger(Log4j2ApplicationTests.class);

    /**
     * 定时任务类日志，这里的name必须和XML中logger 的name一致，这里为 schedule
     */
    private final Logger scheduleLogger = LoggerFactory.getLogger("schedule");

    /**
     * 异步处理日志
     */
    private final Logger asyncLogger = LoggerFactory.getLogger("asyncLog");

    @Autowired
    private MyService myService;

    /**
     * 普通日志测试
     */
    @Test
    void normalLogTest() {
        normalLogger.debug("this is a debug log with out args");
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
        normalLogger.debug("this is a debug log with args");
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
        scheduleLogger.debug("this is a schedule debug log");
        scheduleLogger.info("this is a schedule info log");
        scheduleLogger.error("this is a schedule error log");
        // 指定包或者类
        myService.myLog();
    }

    /**
     * 异步日志
     */
    @Test
    void asyncLogTest() {
        asyncLogger.debug("this is a async log -- debug");
        asyncLogger.info("this is a begin async log -- info");
        asyncLogger.error("this is a begin async log -- error");
    }

}
