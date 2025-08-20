package com.monocept.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monocept.response.Meta;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Meta meta = getMeta(authException);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, meta);
        outputStream.flush();
    }

    private static Meta getMeta(AuthenticationException authException) {
        String errorMessage = "Invalid user name or password";

        if (authException instanceof BadCredentialsException) {
            errorMessage = "Invalid user name or password";
        } else if (authException instanceof DisabledException) {
            errorMessage = "Account is disabled";
        } else if (authException instanceof LockedException) {
            errorMessage = "Account is locked";
        }

        return new Meta(HttpStatus.UNAUTHORIZED.value(), false, errorMessage);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        Meta meta = new Meta(HttpStatus.UNAUTHORIZED.value(), false, "Access Denied: You do not have permission to access this resource.");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, meta);
        outputStream.flush();
    }
}

