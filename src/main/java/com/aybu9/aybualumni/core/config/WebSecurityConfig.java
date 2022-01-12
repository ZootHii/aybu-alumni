package com.aybu9.aybualumni.core.config;

import com.aybu9.aybualumni.auth.services.AuthManager;
import com.aybu9.aybualumni.core.exception.ForbiddenAccessDeniedHandler;
import com.aybu9.aybualumni.core.exception.UnauthorizedEntryPoint;
import com.aybu9.aybualumni.core.security.filter.AccessTokenAuthenticationFilter;
import com.aybu9.aybualumni.core.security.token.TokenService;
import com.aybu9.aybualumni.fake_obs.services.FakeOBSDataService;
import com.aybu9.aybualumni.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.secret}")
    protected String secret;

    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final UserService userService;
    private final FakeOBSDataService fakeOBSDataService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public WebSecurityConfig(ObjectMapper objectMapper, TokenService tokenService, UserService userService,
                             FakeOBSDataService fakeOBSDataService, HttpServletRequest request,
                             HttpServletResponse response) {
        this.objectMapper = objectMapper;
        this.tokenService = tokenService;
        this.userService = userService;
        this.fakeOBSDataService = fakeOBSDataService;
        this.request = request;
        this.response = response;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/auths/login", "/auths/register", "/auths/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())
                .accessDeniedHandler(forbiddenAccessDeniedHandler())
                .and()
                .addFilterAfter(new AccessTokenAuthenticationFilter(tokenService, userService),
                        UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.HEAD);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addExposedHeader(HttpHeaders.CONTENT_DISPOSITION);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return new UnauthorizedEntryPoint(objectMapper);
    }

    @Bean
    public AccessDeniedHandler forbiddenAccessDeniedHandler() {
        return new ForbiddenAccessDeniedHandler(objectMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder(secret);
        pbkdf2PasswordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
        return pbkdf2PasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new AuthManager(userService, tokenService, fakeOBSDataService,
                request, response, passwordEncoder()));
    }
}
