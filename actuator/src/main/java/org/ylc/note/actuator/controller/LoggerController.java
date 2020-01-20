package org.ylc.note.actuator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 日志级别调整
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/20
 */
@RestController
@RequestMapping("/logger")
public class LoggerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/init")
    public String init() {
        logger.info("这是初始化 Info 数据");
        logger.error("这是初始化 Error 数据");
        return "初始化完成";
    }

    @GetMapping("/level")
    public String level() {
        String level;
        if (logger.isDebugEnabled()) {
            level = "debug";
        } else if (logger.isInfoEnabled()) {
            level = "info";
        } else if (logger.isWarnEnabled()) {
            level = "warn";
        } else if (logger.isErrorEnabled()) {
            level = "error";
        } else {
            level = "other";
        }
        return level;
    }
}
