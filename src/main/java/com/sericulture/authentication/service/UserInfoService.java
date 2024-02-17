package com.sericulture.authentication.service;

import com.sericulture.authentication.model.AuthApiResponse;
import com.sericulture.authentication.model.entity.UserInfo;
import com.sericulture.authentication.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByUsername(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public AuthApiResponse registerUser(UserInfo userInfo) {
        AuthApiResponse authApiResponse = new AuthApiResponse();
        if(userInfoRepository.findByUsername(userInfo.getUsername()).isPresent()) {
            authApiResponse.setError(1);
            authApiResponse.setMessage("Username already registered, please use a different username or try logging in!");
        }
        else {
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            userInfoRepository.save(userInfo);
            authApiResponse.setError(0);
            authApiResponse.setMessage("User Added Successfully!");
        }
        return authApiResponse;
    }

}
