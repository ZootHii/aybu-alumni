package com.aybu9.aybualumni.core.security.filter;

import com.aybu9.aybualumni.core.security.token.TokenService;
import com.aybu9.aybualumni.user.services.UserService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aybu9.aybualumni.core.security.Constants.ACCESS_TOKEN;

public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(AccessTokenAuthenticationFilter.class);

    private final TokenService tokenService;
    private final UserService userService;

    public AccessTokenAuthenticationFilter(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        var cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
//        if (Strings.isNullOrEmpty(token)) {
//            token = request.getHeader(ACCESS_TOKEN); // IF WANNA USE HEADER
//        }
        if (!Strings.isNullOrEmpty(token)) {
            var bodyDataResult = tokenService.getBodyFromToken(token);
            if (!bodyDataResult.isSuccess()) {
                logger.error("Possible error {}", bodyDataResult.getMessage());
                throw new IOException(bodyDataResult.getMessage());
            }

            var bodyData = bodyDataResult.getData();
            var email = bodyData.getSubject();
            //var authorities = bodyData.get("authorities"); // TODO: 31.10.2021 sonra hallet roller vesaire video dk 4:27:33

            var userDataResult = userService.getByEmail(email);
            if (!userDataResult.isSuccess()) {
                logger.error("Possible error {}", userDataResult.getMessage());
                throw new IOException(userDataResult.getMessage()); 
            }
            var userData = userDataResult.getData();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userData.getEmail(),
                    userData.getPassword()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
