package com.sericulture.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginApiResponse {
    private int error;
    private String message;
    private String token;
    private String username;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long userMasterId;
    private Long roleId;
    private String phoneNumber;
    private int marketId;
    private int userType;
    private Long userTypeId;
    private String deviceId;
    private int godownId;
    private String marketName;
}
