package com.learning.electronic.bean.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/28 - 10:24
 * @history 2020/4/28 - 10:24 chenglonghy  create.
 */
@Data
public class UserInfo implements UserDetails {

    private int id;
    private String username;
    private String password;
    private List<Role> roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //账户过期
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    //账户锁定
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    //密码过期
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    //账户可用
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}