package org.ylc.note.actuator;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/1/20
 */
@Configuration
public class CustomAuditEventRepository implements AuditEventRepository {
    @Override
    public void add(AuditEvent event) {

    }

    @Override
    public List<AuditEvent> find(String principal, Instant after, String type) {
        return null;
    }
}
