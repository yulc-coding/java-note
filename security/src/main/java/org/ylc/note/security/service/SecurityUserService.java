package org.ylc.note.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ylc.note.security.entity.SecurityUserDetails;
import org.ylc.note.security.entity.User;
import org.ylc.note.security.mapper.MenuMapper;
import org.ylc.note.security.mapper.UserMapper;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/12
 */
@Slf4j
@Service
public class SecurityUserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final MenuMapper menuMapper;

    public SecurityUserService(UserMapper userMapper, MenuMapper menuMapper) {
        this.userMapper = userMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名【{}】获取用户信息", username);
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setUsername(user.getUsername());
        securityUserDetails.setPassword(user.getPassword());

        log.info("获取用户【{}】的权限", username);
        // 权限
        securityUserDetails.setPermissions(menuMapper.getUserPermissions(user.getId()));
        return securityUserDetails;
    }

}
