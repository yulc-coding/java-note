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

    /**
     * 默认交换机：AMQP default
     */
    @GetMapping("/default")
    public String sendDefault() {
        rabbitTemplate.convertAndSend("defaultQueue", getMessage("default"));
        return "success";
    }

    /**
     * 直连交换机
     */
    @GetMapping("/direct")
    public String sendDirectMessage() {
        rabbitTemplate.convertAndSend("directExchange", "directRouting", getMessage("direct"));
        return "success";
    }

    /**
     * 主题交换机1
     */
    @GetMapping("/topicFirst")
    public String sendTopicFirst() {
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_FIRST, getMessage("topicFirst"));
        return "success";
    }

    /**
     * 主题交换机2
     */
    @GetMapping("/topicSecond")
    public String sendTopicSecond() {
        rabbitTemplate.convertAndSend("topicExchange", TopicRabbitConfig.TOPIC_SECOND, getMessage("topicSecond"));
        return "success";
    }

    /**
     * 扇形交换机
     */
    @GetMapping("/fanout")
    public String sendFanout() {
        rabbitTemplate.convertAndSend("fanoutExchange", null, getMessage("fanout"));
        return "success";
    }


    /**
     * ACK测试：
     * 发送到不存在的交换机
     * 触发：ConfirmCallback
     */
    @GetMapping("/missExchange")
    public String missExchange() {
        rabbitTemplate.convertAndSend("missExchange", "directRouting", getMessage("missExchange"));
        return "success";
    }

    /**
     * ACK测试：
     * 发送到不存在的队列
     * 触发：
     * ConfirmCallback：成功发送到了交换机
     * ReturnCallback：通过路由没有找到对应的队列，返回了NO_ROUTE
     */
    @GetMapping("/missQueue")
    public String missQueue() {
        rabbitTemplate.convertAndSend("unBindingExchange", "directRouting", getMessage("direct"));
        return "success";
    }


    private Map<String, Object> getMessage(String type) {
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);
        map.put("msgId", UUID.randomUUID().toString());
        map.put("msgData", "test message");
        map.put("createTime", createTime);
        return map;
    }

}
