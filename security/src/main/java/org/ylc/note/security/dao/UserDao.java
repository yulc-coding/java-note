package org.ylc.note.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ylc.note.security.entity.User;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author yulc
 * @version 1.0.0
 * @date 2020/5/12
 */
public interface UserDao extends JpaRepository<User, Long> {

    /**
     * 获取用户
     */
    User findUserByUsername(String username);
}
