package com.aybu9.aybualumni.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aybu9.aybualumni.core.security.Constants.APPLICATION_JSON;
import static com.aybu9.aybualumni.core.security.Constants.CONTENT_TYPE;


public record UnauthorizedEntryPoint(ObjectMapper objectMapper) implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        var unauthorized = HttpStatus.UNAUTHORIZED;
        response.setStatus(unauthorized.value());
        response.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        var exceptionDetails = new ExceptionDetails(
                authException.getMessage(),
                "exception",
                authException.getClass().getSimpleName()
        );
        response.getWriter().println(objectMapper.writeValueAsString(exceptionDetails));
    }
}
