package org.ylc.note.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@Component
public class JobTwo {

    public void run() {
        log.info("job2 -> 1分钟执行一次，当前时间：[{}]", LocalDateTime.now().toString());
    }

}

