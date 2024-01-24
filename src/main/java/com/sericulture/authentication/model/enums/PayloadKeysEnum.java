package com.sericulture.authentication.model.enums;

import lombok.Getter;

@Getter
public enum PayloadKeysEnum {
    GODOWNID("godownId"),
    USERNAME("username"),
    USERMASTERID("userMasterId"),
    ROLEID("roleId"),
    PHONE("phoneNumber"),
    MARKETID("marketId"),
    USERTYPE("userType"),
    USERTYPEID("userTypeId"),
    DEVICEID("deviceId");
    private final String name;
    PayloadKeysEnum(String name){
        this.name=name;
    }
    @Override
    public String toString(){
        return name;
    }
}
