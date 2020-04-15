package org.ylc.note.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/15
 */
@Getter
@Setter
public class SecurityUserDetails implements UserDetails {

    private Long id;

    private String username;

    private String password;

    /**
     * 返回授予用户的权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(this.getClass());
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 用户的凭据（密码）是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 指示用户是启用还是禁用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
