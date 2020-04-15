package org.ylc.note.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 校验Token
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/15
 */
public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Qualifier("securityUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 将 ServletRequest 转换为 HttpServletRequest 才能拿到请求头中的 token
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 尝试获取请求头的 token
        String authToken = httpRequest.getHeader("token");
        // 尝试拿 token 中的 username
        // 若是没有 token 或者拿 username 时出现异常，那么 username 为 null
        System.out.println("解析token");
        // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("设置权限");
        }
        chain.doFilter(request, response);
    }

}
