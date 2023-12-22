package com.sericulture.authentication.controller;

import com.sericulture.authentication.model.AuthApiResponse;
import com.sericulture.authentication.model.JwtRequest;
import com.sericulture.authentication.model.LoginApiResponse;
import com.sericulture.authentication.model.RefreshTokenModel;
import com.sericulture.authentication.model.entity.UserInfo;
import com.sericulture.authentication.repository.UserInfoRepository;
import com.sericulture.authentication.service.JwtService;
import com.sericulture.authentication.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserInfoController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${app.rest.role:0}")
    private long roleId;

    //Just to test if correct JWT Token is saved or not
    @GetMapping("/test-token")
    public AuthApiResponse checkToken() {
        AuthApiResponse authApiResponse = new AuthApiResponse();
        authApiResponse.setError(0);
        authApiResponse.setMessage("Welcome to the sample secured endpoint, your token is correct!");
        return authApiResponse;
    }

    @PostMapping("/register")
    public AuthApiResponse registerUser(@RequestBody UserInfo userInfo) {
        userInfo.setRoleId(roleId);
        return service.registerUser(userInfo);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenModel refreshToken(@RequestBody RefreshTokenModel refreshTokenModel) {
        String newToken = jwtService.refreshToken(refreshTokenModel.getToken());
        if(newToken.equals("error"))
        {
            refreshTokenModel.setError(1);
            refreshTokenModel.setMessage("Wrong token passed");
            refreshTokenModel.setToken("");
        }
        else {
            refreshTokenModel.setError(0);
            refreshTokenModel.setMessage("New token generated!");
            refreshTokenModel.setToken(newToken);
        }
        return refreshTokenModel;
    }


    @PostMapping("/login")
    public LoginApiResponse authenticateAndGetToken(@RequestBody JwtRequest authRequest) {
        LoginApiResponse loginApiResponse = new LoginApiResponse();
        Optional<UserInfo> userInfo=userInfoRepository.findByUsername(authRequest.getUsername());
        if(userInfo.isEmpty())
        {
            loginApiResponse.setError(1);
            loginApiResponse.setMessage("Wrong username, please register first!");
        }
        else if(!encoder.matches(authRequest.getPassword(),userInfo.get().getPassword()))
        {
            loginApiResponse.setError(1);
            loginApiResponse.setMessage("Wrong password, please try again!");
        }
        else {
            String jwtToken = jwtService.generateToken(authRequest.getUsername());
            loginApiResponse.setError(0);
            loginApiResponse.setMessage("Correct username and password!");
            loginApiResponse.setToken(jwtToken);
            loginApiResponse.setUserMasterId(userInfo.get().getUserMasterId());
            loginApiResponse.setUsername(userInfo.get().getUsername());
            loginApiResponse.setRoleId(userInfo.get().getRoleId());
            loginApiResponse.setMarketId(userInfo.get().getMarketId());
            loginApiResponse.setPhoneNumber(userInfo.get().getPhoneNumber());
        }
        return loginApiResponse;
    }

}
