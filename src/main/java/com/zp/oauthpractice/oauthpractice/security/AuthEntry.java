package com.zp.oauthpractice.oauthpractice.security;

import java.io.IOException;
import java.io.Serializable;

import javax.security.auth.login.LoginException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntry implements AuthenticationEntryPoint, Serializable {
	private static final Logger logger = LoggerFactory.getLogger(AuthEntry.class);

	// @Autowired
	// private AuthExceptionHandler exceptionHandler;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		var exceptionClass = authException.getClass();

		if (exceptionClass.equals(BadCredentialsException.class)
			|| exceptionClass.equals(LoginException.class)
		) {
			if(exceptionClass.equals(BadCredentialsException.class)) {
					logger.error("Unauthorized access attempt");
			}
		}


	}
	
}
