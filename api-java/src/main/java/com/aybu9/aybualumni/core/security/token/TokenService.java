package com.aybu9.aybualumni.core.security.token;

import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.user.models.User;
import io.jsonwebtoken.Claims;

public interface TokenService {
    DataResult<AccessToken> createToken(User user);
    
    DataResult<Long> getUserIdFromToken(String token);
    
    DataResult<Claims> getBodyFromToken(String token);

    Result validateToken(String token);
}
