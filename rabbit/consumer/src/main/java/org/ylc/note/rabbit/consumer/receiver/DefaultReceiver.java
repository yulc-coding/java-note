package org.ylc.note.rabbit.consumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
@RabbitListener(queues = "defaultQueue")
public class DefaultReceiver {

    @RabbitHandler
    public void process(Map<String, Object> msg) {
        log.info("receiver default message");
        log.info("default type:{}", msg.get("type"));
        log.info("default msgId:{}", msg.get("msgId"));
        log.info("default msgData:{}", msg.get("msgData"));
        log.info("default createTime:{}", msg.get("createTime"));
    }

}
