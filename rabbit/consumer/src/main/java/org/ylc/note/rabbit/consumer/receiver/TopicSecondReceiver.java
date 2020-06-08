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
 * 主题消息订阅
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/8
 */
@Slf4j
@Component
@RabbitListener(queues = "note.second")
public class TopicSecondReceiver {

    @RabbitHandler
    public void process(Map<String, Object> directMsg) {
        log.info("receiver 【topic 2】 message");
        log.info("【topic 2】 type:{}", directMsg.get("type"));
        log.info("【topic 2】 msgId:{}", directMsg.get("msgId"));
        log.info("【topic 2】 msgData:{}", directMsg.get("msgData"));
        log.info("【topic 2】 createTime:{}", directMsg.get("createTime"));
    }

}
