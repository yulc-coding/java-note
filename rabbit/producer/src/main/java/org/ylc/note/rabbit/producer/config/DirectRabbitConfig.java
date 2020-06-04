package org.ylc.note.rabbit.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-06-04
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 配置队列
     */
    @Bean
    public Queue queue() {
        return new Queue("directQueue", true);
    }

    /**
     * 配置交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    /**
     * 将队列和交换机绑定，并设置匹配键
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with("directRouting");
    }
}
