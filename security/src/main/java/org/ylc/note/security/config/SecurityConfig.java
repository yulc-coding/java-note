package org.ylc.note.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.ylc.note.security.service.SecurityUserService;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 配置类
 * <p>
 * EnableGlobalMethodSecurity 开启注解的权限控制，默认是关闭的。
 * prePostEnabled：使用表达式实现方法级别的控制，如：@PreAuthorize("hasRole('ADMIN')")
 * securedEnabled: 开启 @Secured 注解过滤权限，如：@Secured("ROLE_ADMIN")
 * jsr250Enabled: 开启 @RolesAllowed 注解过滤权限，如：@RolesAllowed("ROLE_ADMIN")
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-05-18
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserService securityUserService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    public SecurityConfig(SecurityUserService securityUserService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAccessDeniedHandler customAccessDeniedHandler, CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.securityUserService = securityUserService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    /**
     * 配置密码解密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomUsernamePasswordAuthenticationFilter loginFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    AuthorizationTokenFilter tokenFilter() {
        return new AuthorizationTokenFilter();
    }

    /**
     * 认证相关，用来配置全局的认证相关的信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }

    /**
     * 具体的权限控制规则配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("配置http规则");

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .permitAll()

                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()

                .and()
                // 关闭跨站请求防护
                .csrf().disable()
                // 前后端分离采用JWT 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 异常处理：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint).accessDeniedHandler(customAccessDeniedHandler);
        // 登录过滤器
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        // Token认证过滤器
        http.addFilterAfter(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
