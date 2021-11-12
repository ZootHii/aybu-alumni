package com.aybu9.aybualumni.auth.services;

import com.aybu9.aybualumni.auth.models.dtos.AuthLoginDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthRegisterDto;
import com.aybu9.aybualumni.auth.models.dtos.AuthResponseDto;
import com.aybu9.aybualumni.core.exception.CustomException;
import com.aybu9.aybualumni.core.result.DataResult;
import com.aybu9.aybualumni.core.result.Result;
import com.aybu9.aybualumni.core.result.SuccessDataResult;
import com.aybu9.aybualumni.core.result.SuccessResult;
import com.aybu9.aybualumni.core.security.token.TokenService;
import com.aybu9.aybualumni.user.models.User;
import com.aybu9.aybualumni.user.services.UserService;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.aybu9.aybualumni.core.security.Constants.ACCESS_TOKEN;

@Service
public class AuthManager implements AuthService, AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(AuthManager.class);

    private final UserService userService;
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final PasswordEncoder passwordEncoder;

    public AuthManager(UserService userService, TokenService tokenService, HttpServletRequest request, HttpServletResponse response, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.request = request;
        this.response = response;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public DataResult<AuthResponseDto> register(AuthRegisterDto authRegisterDto) {
        // create new user with hashed password
        // default photo ekleme işlemi
        // profileUrl diye bir şey olsun company de ki gibi kayıt olurken kullanıcıdan alalım unique name olarak
        var name = authRegisterDto.getName();
        var surname = authRegisterDto.getSurname();
        var email = authRegisterDto.getEmail();
        var password = authRegisterDto.getPassword();
        var encodedPassword = passwordEncoder.encode(password);
        var profileUrl = String.format("%s/%s", "http://localhost:4024/users/profile", authRegisterDto.getProfileUrl());
        var userToRegister = new User(name, surname, email, encodedPassword, profileUrl);
        var userCreateDataResult = userService.create(userToRegister);
        var createdUser = userCreateDataResult.getData();
        return onSuccessfulAuthentication(createdUser);
    }

    @Override
    @Transactional
    public DataResult<AuthResponseDto> login(AuthLoginDto authLoginDto) {
        var email = authLoginDto.getEmail();
        var password = authLoginDto.getPassword();

        if (userService.existsByEmail(email)) {
            var user = userService.getByEmail(email).getData();
            if (passwordEncoder.matches(password, user.getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, user.getPassword());
                // todo user içerisinde roller principal lar tanımlanacak ve o authoritiy ler kullanılacak bu sayede authenticated true olur
                //Authentication authentication = new UsernamePasswordAuthenticationToken(email, user.getPassword(), authorities);
                authenticate(authentication);
                return onSuccessfulAuthentication(user);
            }
        }
        throw new CustomException("check email and password");
    }

    @Override
    @Transactional
    public Result logout() {
        return onSuccessfulLogout(ACCESS_TOKEN);
    }

    private DataResult<AuthResponseDto> onSuccessfulAuthentication(User user) {
        var accessTokenDataResult = tokenService.createToken(user);
        var accessToken = accessTokenDataResult.getData();
        var token = accessToken.getToken();
        var cookie = new Cookie(ACCESS_TOKEN, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setDomain(request.getServerName());
        cookie.setPath("/");
        cookie.setMaxAge(3590); // tokendan 10 saniye önce sil saniye cinsi
        response.addCookie(cookie);
        return new SuccessDataResult<>(new AuthResponseDto(accessToken, user), "authentication success");
    }

    private Result onSuccessfulLogout(String... cookiesToClear) { // todo refactor eidlmesi gerek 
        Assert.notNull(cookiesToClear, "List of cookies cannot be null");
        List<Function<HttpServletRequest, Cookie>> cookieList = new ArrayList<>();
        for (String cookieName : cookiesToClear) {
            cookieList.add((request) -> {
                Cookie cookie = new Cookie(cookieName, (String) null);
                String cookiePath = request.getContextPath() + "/";
                cookie.setPath(cookiePath);
                cookie.setMaxAge(0);
                cookie.setSecure(request.isSecure());
                return cookie;
            });
        }
        cookieList.forEach((function) -> response.addCookie(function.apply(request)));
        response.setHeader("Location", "/");
        return new SuccessResult("logout success");
    }

    @Override
    @Transactional
    public User getCurrentUser(Authentication authentication) {
        var authenticatedUserEmail = authentication.getPrincipal().toString();
        if (userService.existsByEmail(authenticatedUserEmail)) {
            return userService.getByEmail(authenticatedUserEmail).getData();
        }
        throw new CustomException("current user");
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}