package com.aybu9.aybualumni.core.security.token;

import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.user.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenManager implements TokenService {

    Logger logger = LoggerFactory.getLogger(TokenManager.class);

    @Value("${token.expiration}")
    private int tokenExpiration;

    @Value("${security.secret}")
    protected String secret;
    
    @Override
    public DataResult<AccessToken> createToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenExpiration);

        try {
            String token = Jwts.builder()
                    .setId(user.getId().toString())
                    .setSubject(user.getEmail())
                    .setIssuedAt(now)
                    .setExpiration(expiration)
                    .signWith(createSecretKey(), SignatureAlgorithm.HS512)
                    .compact();
            return new SuccessDataResult<>(new AccessToken(token, expiration), "token creation success");
        } catch (JwtException exception){
            logger.error("JWT: {}", exception.getMessage());
            throw new CustomException("error token creation");
        }
    }

    @Override
    public DataResult<Long> getUserIdFromToken(String token) {
        
        try {
            var userId = Jwts
                    .parserBuilder()
                    .setSigningKey(createSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getId();
            return new SuccessDataResult<>(Long.parseLong(userId), "getting id from token success");
        } catch (JwtException exception){
            logger.error("JWT: {}", exception.getMessage());
            throw new CustomException("error getting id from token");            
        }
    }

    @Override
    public DataResult<Claims> getBodyFromToken(String token) {
        try {
            var body = Jwts
                    .parserBuilder()
                    .setSigningKey(createSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return new SuccessDataResult<>(body, "getting body from token success");
        } catch (JwtException exception){
            logger.error("JWT: {}", exception.getMessage());
            throw new CustomException("error getting body from token");
        }
    }


    @Override
    public Result validateToken(String token) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(createSecretKey())
                    .build()
                    .parseClaimsJws(token);
            return new SuccessResult("validate token success");
        } catch (JwtException exception) {
            logger.error("JWT: {}", exception.getMessage());
            throw new CustomException("error validate token");
        }
    }

    private SecretKey createSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
