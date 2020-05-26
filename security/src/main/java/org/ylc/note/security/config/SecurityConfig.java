package org.ylc.note.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ylc.note.security.service.SecurityUserService;

import java.util.Collections;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * 配置类
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

    public SecurityConfig(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    /**
     * 配置密码解密
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        log.info("配置密码不加密");
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置角色继承
     * 表示 ROLE_admin 自动具备 ROLE_user 的权限
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        log.info("配置角色继承关系");
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    /**
     * 自定义访问者身份认证
     */
    @Bean
    CustomAuthenticationProvider myAuthenticationProvider() {
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        customAuthenticationProvider.setUserDetailsService(userDetailsService());
        return customAuthenticationProvider;
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(myAuthenticationProvider()));
    }

    /**
     * 用户相关
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("配置用户相关验证");
        auth.userDetailsService(securityUserService);
    }

    @Override
    public void configure(WebSecurity web) {
        log.info("配置忽略校验路径");
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    /**
     * 对资源访问的规则进行重新定义
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("配置http规则");
        http.authorizeRequests()
                .anyRequest().permitAll()
        // .antMatchers("/admin/**").hasRole("admin")
        // .antMatchers("/user/**").hasRole("user")
        // .anyRequest().authenticated()
        // .and()
        // .formLogin().permitAll()
        // .and()
        // .rememberMe()
        // // 自己设置密令后，即使服务器重启也能实现自动登录，该值默认为一个UUID字符串
        // .key("9527")
        // .and()
        // .csrf().disable()
        ;
    }

}
