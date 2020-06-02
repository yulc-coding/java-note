package org.ylc.note.security.config;

import cn.hutool.json.JSONUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.ylc.note.security.common.HttpResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 登入失败
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String msg;
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            msg = "用户名或密码错误，登录失败!";
        } else if (exception instanceof DisabledException) {
            msg = "账户被禁用，登录失败，请联系管理员!";
        } else {
            msg = "登录失败!";
        }
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(HttpResult.fail(msg)));
    }

}
