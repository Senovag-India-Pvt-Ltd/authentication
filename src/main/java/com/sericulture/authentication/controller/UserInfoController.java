package com.sericulture.authentication.controller;

import com.sericulture.authentication.model.AuthApiResponse;
import com.sericulture.authentication.model.JwtRequest;
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
    private int roleId;

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

    @PostMapping("/login")
    public AuthApiResponse authenticateAndGetToken(@RequestBody JwtRequest authRequest) {
        AuthApiResponse authApiResponse = new AuthApiResponse();
        Optional<UserInfo> userInfo=userInfoRepository.findByEmailIdAndRoleId(authRequest.getEmail(),roleId);
        if(userInfo.isEmpty())
        {
            authApiResponse.setError(1);
            authApiResponse.setMessage("Wrong email, please register first!");
        }
        else if(!encoder.matches(authRequest.getPassword(),userInfo.get().getPassword()))
        {
            authApiResponse.setError(1);
            authApiResponse.setMessage("Wrong password, please try again!");
        }
        else{
            String jwtToken = jwtService.generateToken(authRequest.getEmail());
            authApiResponse.setError(0);
            authApiResponse.setMessage("Correct email and password !");
            authApiResponse.setToken(jwtToken);
        }
        return authApiResponse;
    }

}
