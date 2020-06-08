package org.ylc.note.rabbit.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 主题订阅配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/8
 */
@Configuration
public class TopicRabbitConfig {

    public final static String TOPIC_FIRST = "note.first";

    public final static String TOPIC_SECOND = "note.second";

    @Bean
    public Queue firstQueue() {
        return new Queue(TOPIC_FIRST);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue(TOPIC_SECOND);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding bindingTopicFirst() {
        // 只有消息携带的路由键是note.first的,才会分发到该队列
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(TOPIC_FIRST);
    }

    @Bean
    public Binding bindingTopicSecond() {
        // 只有消息携带的路由键是以 note. 开头的的,都会分发到该队列
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("note.#");
    }

}
