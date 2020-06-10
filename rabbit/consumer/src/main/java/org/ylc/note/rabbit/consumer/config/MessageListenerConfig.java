package org.ylc.note.rabbit.consumer.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 消费信息确认
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/10
 */
@Configuration
public class MessageListenerConfig {

    private final CachingConnectionFactory connectionFactory;

    private final MessageAckListener messageAckListener;

    public MessageListenerConfig(CachingConnectionFactory connectionFactory, MessageAckListener messageAckListener) {
        this.connectionFactory = connectionFactory;
        this.messageAckListener = messageAckListener;
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(1);
        // RabbitMQ 默认是自动确认，这里改为手动确认消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置队列，这些队列都是必须已经创建好的
        container.setQueueNames("defaultQueue", "directQueue");
        // 另一种设置队列的方法,如果使用这种情况,那么要设置多个,就使用addQueues
        //container.setQueues(new Queue("TestDirectQueue",true));
        //container.addQueues(new Queue("TestDirectQueue2",true));
        container.setMessageListener(messageAckListener);
        return container;
    }

}
