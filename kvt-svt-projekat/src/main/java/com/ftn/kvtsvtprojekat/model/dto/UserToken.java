package com.ftn.kvtsvtprojekat.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserToken {

    private String accessToken;
    private Long expiresIn;

    public UserToken() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserToken(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
