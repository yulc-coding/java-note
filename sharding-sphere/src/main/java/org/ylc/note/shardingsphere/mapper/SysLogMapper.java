package org.ylc.note.shardingsphere.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.ylc.note.shardingsphere.entity.SysLog;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/7/24
 */
@Component
public interface SysLogMapper {

    void newLog(@Param("log") SysLog sysLog);

    List<SysLog> rangeSearch(@Param("begin") LocalDateTime begin, @Param("end") LocalDateTime end);

}
