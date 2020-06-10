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
 * <p>
 * 直连型交换机配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-06-04
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 不绑定交换机，会绑定默认的交换机：AMQP default
     */
    @Bean
    public Queue defaultQueue() {
        return new Queue("defaultQueue");
    }

    /**
     * 配置队列
     */
    @Bean
    public Queue directQueue() {
        /*
         * return new Queue("TestDirectQueue", true, false, false);
         * durable:是否持久化,默认是 true,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
         * exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
         * autoDelete:是否自动删除，默认false，当没有生产者或者消费者使用此队列，该队列会自动删除。
         * 一般设置一下队列的持久化就好,其余两个就是默认false
         */
        return new Queue("directQueue", true);
    }

    /**
     * 配置交换机
     */
    @Bean
    public DirectExchange directExchange() {
        /*
         * return new DirectExchange("directExchange", true, false, null);
         * durable:是否持久化,默认是 true
         * autoDelete:是否自动删除，默认 false。
         * 一般设置一下队列的持久化就好,其余两个就是默认false
         */
        return new DirectExchange("directExchange");
    }

    /**
     * 将队列和交换机绑定，并设置匹配键：directRouting
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
    }

}
