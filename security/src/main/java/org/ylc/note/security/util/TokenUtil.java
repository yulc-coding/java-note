package org.ylc.note.security.util;

import org.ylc.note.security.entity.User;

import java.util.UUID;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * Token相关
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/7
 */
public class TokenUtil {

    /**
     * 根据用户生成token
     */
    public static String generateToken(User user) {
        // 实际按照需要的业务生成token，这里模拟，返回随机数
        return UUID.randomUUID().toString();
    }

    /**
     * 解析Token，返回用户信息
     */
    public static User ParseToken(String token) {
        // 模拟解析token过程
        return new User(0L, "admin", "123456");
    }
}
