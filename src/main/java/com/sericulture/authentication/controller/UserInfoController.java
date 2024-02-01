package com.sericulture.authentication.controller;

import com.sericulture.authentication.model.AuthApiResponse;
import com.sericulture.authentication.model.JwtRequest;
import com.sericulture.authentication.model.LoginApiResponse;
import com.sericulture.authentication.model.RefreshTokenModel;
import com.sericulture.authentication.model.entity.MarketMasterInfo;
import com.sericulture.authentication.model.entity.UserInfo;
import com.sericulture.authentication.model.entity.UserPreferenceInfo;
import com.sericulture.authentication.repository.MarketMasterInfoRepository;
import com.sericulture.authentication.repository.UserInfoRepository;
import com.sericulture.authentication.repository.UserPreferenceInfoRepository;
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
    UserPreferenceInfoRepository userPreferenceInfoRepository;

    @Autowired
    MarketMasterInfoRepository marketMasterInfoRepository;

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
//        log.info("Token payload : {},{},{}",
//                jwtService.extractJwtPayload(refreshTokenModel.getToken()).getGodownId(),
//                jwtService.extractJwtPayload(refreshTokenModel.getToken()).getPhoneNumber(),
//                jwtService.extractJwtPayload(refreshTokenModel.getToken()).getUserType());
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
            UserPreferenceInfo userPreference = userPreferenceInfoRepository.findByUserMasterIdAndActive(userInfo.get().getUserMasterId(),true);
            if(userPreference == null){
                loginApiResponse.setGodownId(0);
            }else{
                loginApiResponse.setGodownId(Integer.parseInt(userPreference.getGodownId().toString()));
            }
            MarketMasterInfo marketMasterInfo = marketMasterInfoRepository.findByMarketMasterIdAndActive(userInfo.get().getMarketId(),true);
            if(marketMasterInfo == null){
                loginApiResponse.setMarketName("");
            }else{
                loginApiResponse.setMarketName(marketMasterInfo.getMarketMasterName());
                loginApiResponse.setMarketLat(marketMasterInfo.getMarketLatitude());
                loginApiResponse.setMarketLongitude(marketMasterInfo.getMarketLongitude());
                loginApiResponse.setRadius(marketMasterInfo.getRadius());
            }
            loginApiResponse.setUserMasterId(userInfo.get().getUserMasterId());
            loginApiResponse.setUsername(userInfo.get().getUsername());
            loginApiResponse.setFirstName(userInfo.get().getFirstName());
            loginApiResponse.setLastName(userInfo.get().getLastName());
            loginApiResponse.setEmailId(userInfo.get().getEmailID());
            loginApiResponse.setRoleId(userInfo.get().getRoleId());
            loginApiResponse.setPhoneNumber(userInfo.get().getPhoneNumber());
            loginApiResponse.setMarketId(userInfo.get().getMarketId());
            loginApiResponse.setUserType(userInfo.get().getUserType());
            loginApiResponse.setUserTypeId(userInfo.get().getUserTypeId());
            loginApiResponse.setDeviceId(userInfo.get().getDeviceId());
            String jwtToken = jwtService.generateToken(loginApiResponse,authRequest.getUsername());
            loginApiResponse.setError(0);
            loginApiResponse.setMessage("Correct username and password!");
            loginApiResponse.setToken(jwtToken);
        }
        return loginApiResponse;
    }

}
