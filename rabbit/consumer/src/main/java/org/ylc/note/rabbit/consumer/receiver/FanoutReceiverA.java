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
 * 扇型接收器
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/10
 */
@Slf4j
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {

    @RabbitHandler
    public void process(Map<String, Object> directMsg) {
        log.info("receiver 【fanoutA】 message");
        log.info("【fanoutA】 type:{}", directMsg.get("type"));
        log.info("【fanoutA】 msgId:{}", directMsg.get("msgId"));
        log.info("【fanoutA】 msgData:{}", directMsg.get("msgData"));
        log.info("【fanoutA】 createTime:{}", directMsg.get("createTime"));
    }

}
