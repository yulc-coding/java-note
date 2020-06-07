package org.ylc.note.rabbit.consumer.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 消息接收
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-06-07
 */
@Slf4j
@Component
public class MessageReceiver {

    @RabbitListener(queues = "test queue")
    public void process(Map<String, Object> message) {
        log.info("receiver message");
        log.info("messageId:{}", message.get("messageId"));
        log.info("messageData:{}", message.get("messageData"));
        log.info("createTime:{}", message.get("createTime"));
    }

}
