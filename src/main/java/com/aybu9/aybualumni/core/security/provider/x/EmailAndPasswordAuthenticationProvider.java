//package com.aybu9.aybualumni.core.security.provider;
//
//import com.aybu9.aybualumni.core.security.token.TokenService;
//import com.aybu9.aybualumni.user.services.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import static com.aybu9.aybualumni.core.security.Constants.ACCESS_TOKEN;
//
//public class EmailAndPasswordAuthenticationProvider implements AuthenticationProvider {
//    
//    Logger logger = LoggerFactory.getLogger(EmailAndPasswordAuthenticationProvider.class); 
//    
//    private final UserService userService;
//    private final TokenService tokenService;
//    private final HttpServletRequest httpServletRequest;
//    private final HttpServletResponse httpServletResponse;
//
//    public EmailAndPasswordAuthenticationProvider(UserService userService, TokenService tokenService, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        this.userService = userService;
//        this.tokenService = tokenService;
//        this.httpServletRequest = httpServletRequest;
//        this.httpServletResponse = httpServletResponse;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var email = authentication.getPrincipal().toString(); // as username in basic auth
//        var password = authentication.getCredentials().toString();
//        
//        var userDataResult = userService.checkCredentials(email, password);
//        if (!userDataResult.isSuccess()){
//            logger.error(userDataResult.isSuccess() + " " + userDataResult.getMessage(), userDataResult.getData());
//            throw new BadCredentialsException("Check email and password!"); // todo return new exception message 
//        } else {
//
//            //var email = authResult.getPrincipal();
//            var user= userService.getByEmail(email);
//            var accessToken = tokenService.createToken(user.getData());
//            var cookie = new Cookie(ACCESS_TOKEN, accessToken.getToken());
//            cookie.setHttpOnly(true);
//            cookie.setSecure(httpServletRequest.isSecure());
//            cookie.setDomain(httpServletRequest.getServerName());
//            cookie.setPath("/");
//            httpServletResponse.addCookie(cookie);
//            
//            return new UsernamePasswordAuthenticationToken(userDataResult.getData().getEmail(), userDataResult.getData().getPassword());
//        }
//    }
//
//    
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
