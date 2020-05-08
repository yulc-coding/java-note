package org.ylc.note.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Qualifier("securityUserDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 尝试获取请求头的 token
        String authToken = request.getHeader("token");
        // 请求方法，用于判断是否有权限
        String servletPath = request.getServletPath();

        // 尝试拿 token 中的 username
        System.out.println("解析token，获取username");
        // 获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername("");
        // 如果上面解析 token 成功并且拿到了 username 并且本次会话的权限还未被写入
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("设置权限");
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
