package org.ylc.note.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.ylc.note.security.entity.SecurityUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * 访问权限认证
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-05-26
 */
@Service
public class AccessAuthorityService {

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();
        if (userInfo instanceof UserDetails) {
            Set<GrantedAuthority> permission = (Set<GrantedAuthority>) ((SecurityUserDetails) userInfo).getAuthorities();

            String requestPath = request.getRequestURI();
            for (GrantedAuthority grantedAuthority : permission) {
                if (grantedAuthority.getAuthority().equals(requestPath)) {
                    return true;
                }
            }
        }
        return false;
    }
}
