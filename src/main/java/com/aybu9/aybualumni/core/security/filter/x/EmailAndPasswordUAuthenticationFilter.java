//package com.aybu9.aybualumni.core.security.filter;
//
//import com.aybu9.aybualumni.core.security.token.TokenService;
//import com.aybu9.aybualumni.user.models.User;
//import com.aybu9.aybualumni.user.services.UserService;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.AuthenticationEntryPoint;
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
//public class EmailAndPasswordUAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final TokenService tokenService;
//    private final UserService userService;
//
//    public EmailAndPasswordUAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, TokenService tokenService, UserService userService) {
//        super(authenticationManager);
//        this.tokenService = tokenService;
//        this.userService = userService;
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        var email = authResult.getPrincipal();
//        var user= userService.getByEmail(email.toString());
//        var accessToken = tokenService.createToken(user.getData());
//        var cookie = new Cookie(ACCESS_TOKEN, accessToken.getToken());
//        cookie.setHttpOnly(true);
//        cookie.setSecure(request.isSecure());
//        cookie.setDomain(request.getServerName());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//    }
//}
