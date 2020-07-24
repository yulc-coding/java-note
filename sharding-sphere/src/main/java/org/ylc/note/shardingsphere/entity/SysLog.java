package org.ylc.note.shardingsphere.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/7/24
 */
@Getter
@Setter
@ToString
public class SysLog {

    private Integer id;

    private Integer value;

    private LocalDateTime createTime;
}
