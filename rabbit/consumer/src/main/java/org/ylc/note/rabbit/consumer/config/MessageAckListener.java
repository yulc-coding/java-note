package org.ylc.note.rabbit.consumer.config;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 手动确认消息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/6/10
 */
@Slf4j
@Component
public class MessageAckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 因为传递消息的时候用的map传递,所以将Map从Message内取出需要做些处理
            String msg = message.toString();
            // 可以点进Message里面看源码,单引号直接的数据就是我们的map消息数据
            String[] msgArray = msg.split("'");
            Map<String, String> msgMap = mapStringToMap(msgArray[1].trim());
            log.info("【AckReceiver】");
            log.info("type:{}", msgMap.get("type"));
            log.info("msgId:{}", msgMap.get("msgId"));
            log.info("msgData:{}", msgMap.get("msgData"));
            log.info("createTime:{}", msgMap.get("createTime"));
            String queue = message.getMessageProperties().getConsumerQueue();
            log.info("消费的主题消息来自：{}", queue);
            if ("defaultQueue".equals(queue)) {
                log.info("这是一个默认交换机的队列");
            } else if ("directQueue".equals(queue)) {
                log.info("这是一个直连交换机的队列");
            }
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            log.error("消费信息异常，重回队列");
            // 为true会重新放回队列
            channel.basicReject(deliveryTag, true);
            e.printStackTrace();
        }
    }

    /**
     * 格式转换成map
     * {key=value,key=value,key=value} 格式转换成map
     */
    private Map<String, String> mapStringToMap(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strArr = str.split(",");
        Map<String, String> map = new HashMap<>();
        for (String string : strArr) {
            String key = string.split("=")[0].trim();
            String value = string.split("=")[1];
            map.put(key, value);
        }
        return map;
    }
}
