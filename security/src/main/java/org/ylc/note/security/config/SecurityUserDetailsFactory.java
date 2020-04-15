package org.ylc.note.security.config;

import org.ylc.note.security.entity.User;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/15
 */
public class SecurityUserDetailsFactory {

    private SecurityUserDetailsFactory() {

    }

    public static SecurityUserDetails create(User user) {
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setId(user.getId());
        securityUserDetails.setPassword(user.getPassword());
        securityUserDetails.setUsername(user.getUsername());

        return securityUserDetails;
    }

}
