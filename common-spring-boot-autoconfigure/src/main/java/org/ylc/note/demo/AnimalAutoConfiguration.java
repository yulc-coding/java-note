package org.ylc.note.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 自动配置
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/25
 */
//是个配置类
@Configuration
// 指明配置类，这样AnimalProperties才能被注入
@EnableConfigurationProperties(AnimalProperties.class)
// 在web应用中起效
// @ConditionalOnWebApplication
@ConditionalOnClass(AnimalProperties.class)
// @ConditionalOnProperty(prefix = "animal", value = "enable", matchIfMissing = true)
public class AnimalAutoConfiguration {

    @Autowired
    private AnimalProperties animalProperties;

    /**
     * 只有满足当FishEnvironmentCondition.matches 返回true时才实例化FishService
     * 当容器中没有这个Bean时才注入
     *
     * @return FishService
     */
    @Bean
    @Conditional(FishEnvironmentCondition.class)
    @ConditionalOnMissingBean(FishService.class)
    public FishService fishService() {
        return new FishService(animalProperties);
    }

    /**
     * BirdEnvironmentCondition.matches BirdService
     *
     * @return BirdService
     */
    @Bean
    @Conditional(BirdEnvironmentCondition.class)
    @ConditionalOnMissingBean(BirdService.class)
    public BirdService birdService() {
        return new BirdService(animalProperties);
    }

}
