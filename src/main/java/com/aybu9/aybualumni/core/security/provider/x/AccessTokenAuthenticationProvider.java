//package com.aybu9.aybualumni.core.security.provider;
//
//import com.aybu9.aybualumni.core.security.token.TokenService;
//import com.aybu9.aybualumni.user.services.UserService;
//import io.jsonwebtoken.JwtException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//
//public class AccessTokenAuthenticationProvider implements AuthenticationProvider {
//
//    Logger logger = LoggerFactory.getLogger(AccessTokenAuthenticationProvider.class);
//
//    private final TokenService tokenService;
//    private final UserService userService;
//
//    public AccessTokenAuthenticationProvider(TokenService tokenService, UserService userService) {
//        this.tokenService = tokenService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var token = authentication.getPrincipal().toString();
//        if (!token.contains("User") && !token.contains("@")){ // check if principal is token
//            try {
//                tokenService.validateToken(token);
//                var userId = tokenService.getUserIdFromToken(token);
//                var userResult = userService.get(userId);
//                if (!userResult.isSuccess()) {
//                    throw new BadCredentialsException(userResult.getMessage()); // todo return new exception message
//                }
//                return new PreAuthenticatedAuthenticationToken(userResult.getData(), null);
//            } catch (JwtException jwtException) {
//                throw new BadCredentialsException("Token error!");
//            }    
//        }
//        return authentication;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.equals(PreAuthenticatedAuthenticationToken.class);
//    }
//}
