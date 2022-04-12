package com.aybu9.aybualumni.core.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aybu9.aybualumni.core.security.Constants.APPLICATION_JSON;
import static com.aybu9.aybualumni.core.security.Constants.CONTENT_TYPE;

public record ForbiddenAccessDeniedHandler(ObjectMapper objectMapper) implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        var forbidden = HttpStatus.FORBIDDEN;
        response.setStatus(forbidden.value());
        response.addHeader(CONTENT_TYPE, APPLICATION_JSON);
        var exceptionDetails = new ExceptionDetails(
                accessDeniedException.getMessage(),
                "exception",
                accessDeniedException.getClass().getSimpleName()
        );
        response.getWriter().println(objectMapper.writeValueAsString(exceptionDetails));
    }
}
