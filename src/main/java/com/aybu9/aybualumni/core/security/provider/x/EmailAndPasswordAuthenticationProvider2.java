//package com.aybu9.aybualumni.core.security.provider;
//
//import com.aybu9.aybualumni.core.exception.CustomException;
//import com.aybu9.aybualumni.user.services.UserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EmailAndPasswordAuthenticationProvider2 implements AuthenticationProvider {
//
//    Logger logger = LoggerFactory.getLogger(EmailAndPasswordAuthenticationProvider2.class); 
//
//    private final UserService userService;
//
//    public EmailAndPasswordAuthenticationProvider2(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        var email = authentication.getPrincipal().toString();
//        var password = authentication.getCredentials().toString();
//        
//        var userDataResult = userService.checkCredentials(email, password);
//        if (!userDataResult.isSuccess()){
//            logger.error("Possible error {}", userDataResult.getMessage());
//            throw new BadCredentialsException("Check email and password!"); 
//        } else {
//            return new UsernamePasswordAuthenticationToken(email, password);
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.equals(UsernamePasswordAuthenticationToken.class);
//    }
//}
