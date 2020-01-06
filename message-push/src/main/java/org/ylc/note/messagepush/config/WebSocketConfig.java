package org.ylc.note.messagepush.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * WebSocket配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-01-06
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
