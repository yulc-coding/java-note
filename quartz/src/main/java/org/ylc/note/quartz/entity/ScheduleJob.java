package org.ylc.note.quartz.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 定时任务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-12
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "schedule_job")
public class ScheduleJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    private String parameter;

    @Column(name = "cron_expression")
    private String cronExpression;

    private String status;

    @Column(name = "job_sched")
    private String jobSched;

    private String remark;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
