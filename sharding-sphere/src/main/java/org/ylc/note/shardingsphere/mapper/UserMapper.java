package org.ylc.note.shardingsphere.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.ylc.note.shardingsphere.entity.User;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author Yulc
 * @date 2020/7/29
 */
@Component
public interface UserMapper {

    User searchUser(@Param("username") String username);
}
