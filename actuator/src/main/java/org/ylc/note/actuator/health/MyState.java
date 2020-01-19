package org.ylc.note.actuator.health;

import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.health.StatusAggregator;

import java.util.Set;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/19
 */
public class MyState implements StatusAggregator {
    @Override
    public Status getAggregateStatus(Set<Status> statuses) {
        return null;
    }
}
