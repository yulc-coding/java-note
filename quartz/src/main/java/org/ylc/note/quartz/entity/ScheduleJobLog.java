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
 * 定时任务执行日志
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-09-17
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "schedule_job_log")
public class ScheduleJobLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    private String parameter;

    /**
     * 执行时间
     */
    @Column(name = "execution_time")
    private Long executionTime;

    /**
     * 执行状态
     */
    @Column(name = "execution_status")
    private String executionStatus;

    /**
     * 错误信息
     */
    @Column(name = "err_msg")
    private String errMsg;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
