package org.ylc.note.security.config;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.ylc.note.security.common.HttpResult;
import org.ylc.note.security.entity.SecurityUserDetails;
import org.ylc.note.security.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 登录成功
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 可以重定向
        // response.sendRedirect("xxx");
        // 返回 token
        SecurityUserDetails user = (SecurityUserDetails) authentication.getPrincipal();
        String token = TokenUtil.generator(user.getUsername());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(HttpResult.success(token)));
    }
}
