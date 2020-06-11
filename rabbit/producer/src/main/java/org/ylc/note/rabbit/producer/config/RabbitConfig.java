package org.ylc.note.rabbit.producer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-06-07
 */
@Slf4j
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        // 设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        // 消息发送到交换机，ack为true，如果要发送的交换机不存在，ack为false
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback:     相关数据：{}", correlationData);
            log.info("ConfirmCallback:     确认情况：{}", ack);
            log.info("ConfirmCallback:     原因：{}", cause);
        });
        // 消息到达交换机，但是根据路由找不到对应的队列时会调用
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("ReturnCallback:     消息：{}", message);
            log.info("ReturnCallback:     回应码：{}", replyCode);
            log.info("ReturnCallback:     回应信息：{}", replyText);
            log.info("ReturnCallback:     交换机：{}", exchange);
            log.info("ReturnCallback:     路由键：{}", routingKey);
        });
        return rabbitTemplate;
    }

}
