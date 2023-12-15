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
    private Long userMasterId;
    private Long roleId;
    private String phoneNumber;
}
