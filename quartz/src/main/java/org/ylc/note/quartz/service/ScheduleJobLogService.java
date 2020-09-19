package org.ylc.note.quartz.service;

import org.springframework.stereotype.Service;
import org.ylc.note.quartz.entity.ScheduleJobLog;
import org.ylc.note.quartz.repository.ScheduleJobLogRepository;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-17
 */
@Service
public class ScheduleJobLogService {

    private final ScheduleJobLogRepository scheduleJobLogRepository;

    public ScheduleJobLogService(ScheduleJobLogRepository scheduleJobLogRepository) {
        this.scheduleJobLogRepository = scheduleJobLogRepository;
    }

    public void save(ScheduleJobLog log) {
        scheduleJobLogRepository.save(log);
    }


}
