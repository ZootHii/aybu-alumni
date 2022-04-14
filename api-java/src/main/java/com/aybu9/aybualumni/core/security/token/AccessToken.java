package com.aybu9.aybualumni.core.security.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AccessToken {
    private String token;
    private Date expiration;

    public AccessToken(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
