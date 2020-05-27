package org.ylc.note.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 登录 step 1
 * 用户认证
 * 登录过滤器
 * 主要获取登录的账号密码。
 * 验证码校验等自定义业务
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-05-25
 */
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("login");
        // 登录必须要POST提交
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        /*
         * 验证码校验
         */
        verifyCode(request.getParameter("code"));

        // 获取账号密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        log.info("login step 1 ,用户：{}，密码：{}", username, password);

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void verifyCode(String code) {
        log.info("code:{}", code);
        // 校验验证码
        // if (code == null) {
        //     throw new AuthenticationServiceException("验证码错误");
        // }
    }
}
