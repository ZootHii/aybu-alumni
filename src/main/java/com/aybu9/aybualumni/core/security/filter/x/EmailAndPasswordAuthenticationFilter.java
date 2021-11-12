//package com.aybu9.aybualumni.core.security.filter;
//
//import com.aybu9.aybualumni.core.security.token.TokenService;
//import com.aybu9.aybualumni.user.models.User;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static com.aybu9.aybualumni.core.security.Constants.ACCESS_TOKEN;
//
//public class EmailAndPasswordAuthenticationFilter extends BasicAuthenticationFilter {
//
//    private final TokenService tokenService;
//    
//    public EmailAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
//                                                AuthenticationEntryPoint authenticationEntryPoint, TokenService tokenService) {
//        super(authenticationManager, authenticationEntryPoint);
//        this.tokenService = tokenService;
//    }
//
//    @Override
//    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
//        var user = (User) authResult.getPrincipal();
//        var accessToken = tokenService.createToken(user);
//        var cookie = new Cookie(ACCESS_TOKEN, accessToken.getToken());
//        cookie.setHttpOnly(true);
//        cookie.setSecure(request.isSecure());
//        cookie.setDomain(request.getServerName());
//        cookie.setPath("/");
//        response.addCookie(cookie);
//    }
//}
