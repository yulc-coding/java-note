package org.ylc.note.quartz.job;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-15
 */
@Slf4j
public class JobOne {

    public void run() {
        log.info(LocalDateTime.now().toString());
    }
}
