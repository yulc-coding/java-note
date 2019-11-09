package org.ylc.note.log4j2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 日志测试
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/11/9
 */
@RestController
@RequestMapping("/log")
public class LogTest {

    private final Logger logger = LoggerFactory.getLogger(LogTest.class);

    @GetMapping("/error")
    public String errorTest() {
        logger.info("this is no args info log");
        logger.error("this is no args error log");
        return "success";
    }

    /**
     * 日志中添加自定义参数跟踪
     */
    @GetMapping("/params")
    public String infoTest(@RequestParam(value = "args") String args) {
        MDC.put("user", "ylc");
        MDC.put("args", args);
        logger.info("this is info log with args");
        logger.error("this is error log with args");
        return "success";
    }


}
