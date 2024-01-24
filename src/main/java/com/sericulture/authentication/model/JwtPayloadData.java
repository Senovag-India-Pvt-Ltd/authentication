package com.sericulture.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayloadData {
    private String username;
    private Long userMasterId;
    private Long roleId;
    private String phoneNumber;
    private int marketId;
    private int userType;
    private Long userTypeId;
    private String deviceId;
    private int godownId;
}
