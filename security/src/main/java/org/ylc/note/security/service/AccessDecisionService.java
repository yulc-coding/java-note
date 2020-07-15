package org.ylc.note.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.ylc.note.security.constant.ConfigConstants;
import org.ylc.note.security.entity.SecurityUserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 动态URL权限验证
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-07-15
 */
@Service
public class AccessDecisionService {

    private final RedisService redisService;

    public AccessDecisionService(RedisService redisService) {
        this.redisService = redisService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object userInfo = authentication.getPrincipal();

        boolean hasPermission = false;

        if (userInfo instanceof SecurityUserDetails) {
            Long userId = ((SecurityUserDetails) userInfo).getUserId();
            // 从redis中匹配当前请求是否有权限
            String curUri = request.getRequestURI();
            hasPermission = redisService.memberInSet(ConfigConstants.USER_PERMISSION_PREFIX + userId, curUri);
        }
        return hasPermission;
    }

}
