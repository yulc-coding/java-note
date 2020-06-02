package org.ylc.note.security.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.ylc.note.security.common.HttpResult;
import org.ylc.note.security.constant.ConfigConstants;
import org.ylc.note.security.entity.SecurityUserDetails;
import org.ylc.note.security.mapper.MenuMapper;
import org.ylc.note.security.service.RedisService;
import org.ylc.note.security.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 登录成功
 * 生成并缓存Token
 * 缓存用户权限列表
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MenuMapper menuMapper;

    private final RedisService redisService;

    public CustomAuthenticationSuccessHandler(MenuMapper menuMapper, RedisService redisService) {
        this.menuMapper = menuMapper;
        this.redisService = redisService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("获取认证信息");
        SecurityUserDetails user = (SecurityUserDetails) authentication.getPrincipal();

        long userId = user.getUserId();
        String token = TokenUtil.generator(String.valueOf(userId));

        List<String> permissions = menuMapper.getUserPermissions(userId);
        log.info("获取用户权限，数量：【{}】", permissions.size());

        // 缓存Token和权限
        redisService.set(
                ConfigConstants.USER_TOKEN_PREFIX + userId,
                token,
                ConfigConstants.DEFAULT_TOKEN_INVALID_TIME
        );
        redisService.delete(ConfigConstants.USER_PERMISSION_PREFIX + userId);
        if (permissions.size() > 0) {
            redisService.strSetAdd(
                    ConfigConstants.USER_PERMISSION_PREFIX + userId,
                    ConfigConstants.DEFAULT_TOKEN_INVALID_TIME,
                    listToArr(permissions)
            );
        }

        // 可以重定向
        // response.sendRedirect("xxx");
        // 返回 token
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(HttpResult.success(token)));
    }

    /**
     * list转数组
     */
    private String[] listToArr(List<String> list) {
        String[] arr = new String[list.size()];
        int index = 0;
        for (String str : list) {
            arr[index++] = str;
        }
        return arr;
    }
}
