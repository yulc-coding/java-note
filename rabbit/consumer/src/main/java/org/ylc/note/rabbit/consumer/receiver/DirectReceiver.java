package org.ylc.note.rabbit.consumer.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 直接交换机消费
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/8
 */
@Slf4j
@Component
@RabbitListener(queues = "directQueue")
public class DirectReceiver {

    @RabbitHandler
    public void handler(Map<String, Object> directMsg) {
        log.info("receiver direct message");
        log.info("type:{}", directMsg.get("type"));
        log.info("msgId:{}", directMsg.get("msgId"));
        log.info("msgData:{}", directMsg.get("msgData"));
        log.info("createTime:{}", directMsg.get("createTime"));
    }

}
