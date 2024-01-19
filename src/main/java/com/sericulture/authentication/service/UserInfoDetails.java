package com.sericulture.authentication.service;

import com.sericulture.authentication.model.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

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
