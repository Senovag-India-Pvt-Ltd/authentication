package com.sericulture.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthApiResponse {

    private int error;
    private String message;
    private String token;
}
