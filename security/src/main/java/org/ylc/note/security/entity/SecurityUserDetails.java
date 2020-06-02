package org.ylc.note.security.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/26
 */
@Slf4j
@Setter
@Getter
public class SecurityUserDetails implements UserDetails {

    private Long userId;

    private String username;

    private String password;

    private List<String> permissions = Collections.emptyList();

    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("生成权限");
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.permissions.forEach(o -> authorities.add(new SimpleGrantedAuthority(o)));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
