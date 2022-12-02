package com.zp.oauthpractice.oauthpractice.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutHandlerImpl implements LogoutHandler {
	private static final String sessionEndpoint = "/protocol/openid-connect/logout";
	private static final Logger logger = LoggerFactory.getLogger(LogoutHandlerImpl.class);
	private final RestTemplate template;

	public LogoutHandlerImpl(RestTemplateBuilder builder) {
		this.template = builder.build();
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logout((OidcUser) authentication.getPrincipal());
	}

	private void logout(OidcUser user) {
		UriComponentsBuilder builder = 
			UriComponentsBuilder
				.fromUriString(sessionEndpoint)
				.queryParam("id_token_hint", user.getIdToken().getTokenValue());
		
		ResponseEntity<String> response = template.getForEntity(builder.toUriString(), String.class);

		if (response.getStatusCode().is2xxSuccessful()) {
			logger.info("Successfully logged out");
		} else {
			logger.info("Error occurred attempting to log out");
		}
	}

	
}
