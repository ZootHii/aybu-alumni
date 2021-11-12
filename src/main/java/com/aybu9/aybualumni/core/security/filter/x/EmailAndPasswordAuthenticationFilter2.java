//package com.aybu9.aybualumni.core.security.filter;
//
//import com.aybu9.aybualumni.auth.models.LoginRequest;
//import com.aybu9.aybualumni.core.exception.CustomException;
//import com.aybu9.aybualumni.core.security.token.TokenService;
//import com.aybu9.aybualumni.user.services.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static com.aybu9.aybualumni.core.security.Constants.ACCESS_TOKEN;
//
//public class EmailAndPasswordAuthenticationFilter2 extends UsernamePasswordAuthenticationFilter {
//
//    Logger logger = LoggerFactory.getLogger(EmailAndPasswordAuthenticationFilter2.class);
//
//    private final AuthenticationManager authenticationManager;
//    private final TokenService tokenService;
//    private final UserService userService;
//
//    public EmailAndPasswordAuthenticationFilter2(AuthenticationManager authenticationManager,
//                                                 TokenService tokenService,
//                                                 UserService userService) {
//        this.authenticationManager = authenticationManager;
//        this.tokenService = tokenService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) throws AuthenticationException {
//        try {
//            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    loginRequest.getEmail(),
//                    loginRequest.getPassword()
//            );
//
//            return authenticationManager.authenticate(authentication);
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        var authenticatedUserEmail = authResult.getPrincipal();
//        var userDataResult = userService.getByEmail(authenticatedUserEmail.toString());
//        var accessTokenDataResult = tokenService.createToken(userDataResult.getData());
//        if (!userDataResult.isSuccess()) {
//            logger.error("Possible error {}", userDataResult.getMessage());
//            throw new CustomException("error successful authentication"); // TODO: 31.10.2021 general exception yap 
//        }
//        if (!accessTokenDataResult.isSuccess()) {
//            logger.error("Possible error {}", userDataResult.getMessage());
//            throw new CustomException("error successful authentication"); // TODO: 31.10.2021 general exception yap
//        }
//        var cookie = new Cookie(ACCESS_TOKEN, accessTokenDataResult.getData().getToken());
//        cookie.setHttpOnly(true);
//        cookie.setSecure(request.isSecure());
//        cookie.setDomain(request.getServerName());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//    }
//}
