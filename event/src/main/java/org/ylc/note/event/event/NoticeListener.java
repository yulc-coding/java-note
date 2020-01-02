package org.ylc.note.event.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 消息通知监听
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-02
 */
@Component
public class NoticeListener implements ApplicationListener<NoticeEvent> {

    private static final Logger logger = LoggerFactory.getLogger(NoticeListener.class);

    @Async
    @Override
    public void onApplicationEvent(NoticeEvent noticeEvent) {
        logger.info("listener get event, sleep 2 second...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("event message is : {}", noticeEvent.getMessage());
    }
}
