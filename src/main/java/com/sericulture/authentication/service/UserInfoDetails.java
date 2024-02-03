package com.sericulture.authentication.service;

import com.sericulture.authentication.model.entity.RoleMaster;
import com.sericulture.authentication.model.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserInfoDetails implements UserDetails {
    private String username;
    private String password;
    private Integer marketId;
    private Long userMasterId;

    @Getter
    @Setter
    private String jwtToken;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserInfo userInfo) {
        username = userInfo.getUsername();
        //password = userInfo.getPassword();
        marketId = userInfo.getMarketId();
        userMasterId = userInfo.getUserMasterId();
        if(Objects.nonNull(userInfo.getRole()) && Objects.nonNull(userInfo.getRole().getRoleName())) {
            authorities = List.of(new SimpleGrantedAuthority(userInfo.getRole().getRoleName().toUpperCase()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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
