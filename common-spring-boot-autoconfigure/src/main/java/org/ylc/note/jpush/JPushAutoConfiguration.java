package org.ylc.note.jpush;

import cn.jiguang.common.ServiceHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 自动配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/24
 */
@Configuration
@EnableConfigurationProperties(JPushProperties.class)
@ConditionalOnClass(JPushService.class)
@ConditionalOnProperty(prefix = "j-push", value = "enable", matchIfMissing = true)
public class JPushAutoConfiguration {

    private final JPushProperties jPushProperties;

    public JPushAutoConfiguration(JPushProperties jPushProperties) {
        this.jPushProperties = jPushProperties;
    }

    @Bean
    public JPushService jPushService() {
        String autoCode = ServiceHelper.getBasicAuthorization(jPushProperties.getAppKey(), jPushProperties.getMasterSecret());
        return new JPushService(autoCode);
    }

}
