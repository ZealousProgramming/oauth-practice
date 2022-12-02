package com.zp.oauthpractice.oauthpractice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final LogoutHandlerImpl logoutHandler;

	@Autowired
	private AuthEntry authEntry;

	SecurityConfig(LogoutHandlerImpl logoutHandler) {
		this.logoutHandler = logoutHandler;
	}

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.formLogin()
			.and()
				.authorizeHttpRequests()
					.requestMatchers("/", "/login")
						.permitAll()
					.requestMatchers("/users/**")
						.hasAnyAuthority("user", "admin")
					.requestMatchers("/admin/**")
						.hasAuthority("admin")
					.anyRequest()
					.authenticated();

		http.oauth2Login()
			.and()
				.logout()
				.addLogoutHandler(logoutHandler)
				.logoutSuccessUrl("/");
			// .and()
			// 	.exceptionHandling().authenticationEntryPoint(authEntry);


		return http.build();
	}

	// @Bean
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user = 
	// 		User
	// 			.builder()
	// 			.username("devonmckenzie")
	// 			.password("T0m@havvk")
	// 			.roles("user")
	// 			.build();

	// 		return new InMemoryUserDetailsManager(user);
	// }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
