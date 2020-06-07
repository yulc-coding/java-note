package org.ylc.note.rabbit.producer.config;

import org.springframework.amqp.core.Queue;
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
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return new Queue("test queue");
    }
}
