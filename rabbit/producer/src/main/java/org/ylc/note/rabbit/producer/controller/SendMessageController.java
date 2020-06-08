package org.ylc.note.rabbit.producer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ylc.note.rabbit.producer.config.TopicRabbitConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-06-04
 */
@RestController
@RequestMapping("/send")
public class SendMessageController {

    private final RabbitTemplate rabbitTemplate;

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/default")
    public String sendDefault() {
        rabbitTemplate.convertAndSend("defaultQueue", getMessage("default"));
        return "success";
    }

    @GetMapping("/direct")
    public String sendDirectMessage() {
        rabbitTemplate.convertAndSend("directExchange", "directRouting", getMessage("direct"));
        return "success";
    }

    @GetMapping("/topicFirst")
    public String sendTopicFirst() {
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_FIRST, getMessage("topicFirst"));
        return "success";
    }

    @GetMapping("/topicSecond")
    public String sendTopicSecond() {
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_SECOND, getMessage("topicSecond"));
        return "success";
    }

    private Map<String, Object> getMessage(String type) {
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);
        map.put("msgId", UUID.randomUUID().toString());
        map.put("msgData", "test message, hello!");
        map.put("createTime", createTime);
        return map;
    }

}
