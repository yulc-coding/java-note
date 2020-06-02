package org.ylc.note.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.ylc.note.security.constant.ConfigConstants;
import org.ylc.note.security.entity.SecurityUserDetails;
import org.ylc.note.security.entity.User;
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

    private final RedisService redisService;

    public SecurityUserService(UserMapper userMapper, RedisService redisService) {
        this.userMapper = userMapper;
        this.redisService = redisService;
    }

    /**
     * 登录校验：根据用户名获取用户信息。
     * <p>
     * 生成Token并放入Redis中
     * 获取所有的权限并放入Redis中
     * <p>
     * 校验Token的时候直接从Redis中获取
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名【{}】获取用户信息", username);
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        SecurityUserDetails securityUserDetails = new SecurityUserDetails();
        securityUserDetails.setUserId(user.getId());
        securityUserDetails.setUsername(user.getUsername());
        securityUserDetails.setPassword(user.getPassword());

        return securityUserDetails;
    }

    /**
     * 校验token
     */
    public UserDetails loadUserForAuthorization(String userId) {
        // token失效
        String redisToken = redisService.get(ConfigConstants.USER_TOKEN_PREFIX + userId);
        if (redisToken == null) {
            return null;
        }
        SecurityUserDetails user = new SecurityUserDetails();
        user.setUserId(Long.parseLong(userId));
        user.setToken(redisToken);
        return user;
    }
}
