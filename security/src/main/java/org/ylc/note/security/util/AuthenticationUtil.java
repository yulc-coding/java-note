package org.ylc.note.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 用户信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/13
 */
public class AuthenticationUtil {

    public static Authentication getAuthenticationInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


}
