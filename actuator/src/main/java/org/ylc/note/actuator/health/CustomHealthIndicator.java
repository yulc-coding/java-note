package org.ylc.note.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 自定义健康指标
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/19
 */
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (check()) {
            return Health.up().build();
        }
        return Health.down().withDetail("Error Code", -1).build();
    }

    /**
     * 检查
     */
    public boolean check() {
        return Math.random() > 0.5;
    }
}
