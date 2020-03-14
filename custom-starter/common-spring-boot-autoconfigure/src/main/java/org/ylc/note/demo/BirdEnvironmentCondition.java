package org.ylc.note.demo;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 实例化BirdService的条件
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2019/12/25
 */
public class BirdEnvironmentCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env = context.getEnvironment();
        String[] envs = env.getActiveProfiles();
        for (String e : envs) {
            System.out.println(e);
        }
        return env.containsProperty("animal.bird.doing");
    }

}
