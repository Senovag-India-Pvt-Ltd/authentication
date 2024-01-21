package com.sericulture.authentication.utils;

import com.sericulture.authentication.model.JwtPayloadData;
import com.sericulture.authentication.model.enums.PayloadKeysEnum;
import com.sericulture.authentication.service.JwtService;
import io.jsonwebtoken.Claims;

public class TokenDecrypterUtil {

    /**
     * Function takes token as input and returns all the data of payload
     * @param token
     * @return JwtPayloadData containing all the payload values
     */
    public static JwtPayloadData extractJwtPayload(String token){
        JwtPayloadData jwtPayloadData=new JwtPayloadData();
        JwtService jwtService = new JwtService();
        final Claims claims = jwtService.extractAllClaims(token);
        jwtPayloadData.setGodownId(claims.get("godownId",Integer.class));
        jwtPayloadData.setUsername(claims.get("username",String.class));
        jwtPayloadData.setUserMasterId(claims.get("userMasterId",Long.class));
        jwtPayloadData.setRoleId(claims.get("roleId",Long.class));
        jwtPayloadData.setPhoneNumber(claims.get("phoneNumber",String.class));
        jwtPayloadData.setMarketId(claims.get("marketId",Integer.class));
        jwtPayloadData.setUserType(claims.get("userType",Integer.class));
        jwtPayloadData.setUserTypeId(claims.get("userTypeId",Long.class));
        jwtPayloadData.setDeviceId(claims.get("deviceId",String.class));
        return jwtPayloadData;
    }


    /**
     * Function takes token and key as parameter and return it's value from payload
     * @param token
     * @param payloadKeysEnum
     * @return string
     */
    public static String extractJwtPayload(String token, PayloadKeysEnum payloadKeysEnum){
        JwtService jwtService = new JwtService();
        final Claims claims = jwtService.extractAllClaims(token);
        return claims.get(payloadKeysEnum.toString(),String.class);
    }
}
