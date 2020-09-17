package org.ylc.note.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ylc.note.quartz.entity.ScheduleJobLog;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 定时任务操作日志数据库操作接口
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-17
 */
public interface ScheduleJobLogRepository extends JpaRepository<ScheduleJobLog, Long> {

}
