package org.ylc.note.security.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ylc.note.security.entity.User;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 获取用户信息
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/15
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("根据用户名获取用户信息");
        User user = new User();
        return SecurityUserDetailsFactory.create(user);
    }

}
