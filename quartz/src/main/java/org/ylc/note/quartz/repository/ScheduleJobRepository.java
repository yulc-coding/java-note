package org.ylc.note.quartz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ylc.note.quartz.entity.ScheduleJob;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 定时任务数据库操作接口
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-12
 */
public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long> {
}
