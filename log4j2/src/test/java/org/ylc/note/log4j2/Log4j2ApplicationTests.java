package org.ylc.note.log4j2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;
import org.ylc.note.log4j2.controller.LogTest;

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
     * 监控类日志测试
     */
    @Test
    void scheduleLogTest() {
        scheduleLogger.info("this is a schedule info log");
        scheduleLogger.info("this is a schedule error log");
    }

}
