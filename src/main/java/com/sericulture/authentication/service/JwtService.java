package com.sericulture.authentication.service;

import com.sericulture.authentication.model.LoginApiResponse;
import com.sericulture.authentication.model.entity.UserInfo;
import com.sericulture.authentication.repository.UserInfoRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Value("${jwt.expire.minutes:30}")
    private int jwtExpiringMinutes;

    public static final String SECRET = "74c47decc64fd921299567f5f6467860dc9179ce2e723048c184fdf2fd6a32936470ecc3d639b6947e99f9c42735ed20552be14fda24084ad79627195aca3fb1";

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * jwtExpiringMinutes))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates token when valid username is passed
     * @param loginApiResponse
     * @param userName
     * @return
     */

    public String generateToken(LoginApiResponse loginApiResponse, String userName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("godownId",loginApiResponse.getGodownId());
        claims.put("username",loginApiResponse.getUsername());
        claims.put("userMasterId",loginApiResponse.getUserMasterId());
        claims.put("roleId",loginApiResponse.getRoleId());
        claims.put("phoneNumber",loginApiResponse.getPhoneNumber());
        claims.put("marketId",loginApiResponse.getMarketId());
        claims.put("userType",loginApiResponse.getUserType());
        claims.put("userTypeId",loginApiResponse.getUserTypeId());
        claims.put("deviceId",loginApiResponse.getDeviceId());
        return createToken(claims, userName);
    }

    /**
     * This function generates a new token from previous token with valid username as subject
     *
     * @param token
     * @return String
     */

    public String refreshToken(String token) {
        try {
            final String username = extractUsername(token);
            Optional<UserInfo> userInfo=userInfoRepository.findByUsername(username);
            if(userInfo.isEmpty()){
                return "error";
            }
            else
            {
                LoginApiResponse loginApiResponse=new LoginApiResponse();
                loginApiResponse.setError(0);
                loginApiResponse.setMessage("Correct username and password!");
                loginApiResponse.setUserMasterId(userInfo.get().getUserMasterId());
                loginApiResponse.setUsername(userInfo.get().getUsername());
                loginApiResponse.setRoleId(userInfo.get().getRole().getRoleId());
                loginApiResponse.setPhoneNumber(userInfo.get().getPhoneNumber());
                loginApiResponse.setMarketId(userInfo.get().getMarketId());
                loginApiResponse.setUserType(userInfo.get().getUserType());
                loginApiResponse.setUserTypeId(userInfo.get().getUserTypeId());
                loginApiResponse.setDeviceId(userInfo.get().getDeviceId());
                return generateToken(loginApiResponse,username);
            }
        }
        catch (Exception e){
            return "error";
        }
    }
}
