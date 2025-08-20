package com.bill.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	private final UserIdHeaderFilter userIdHeaderFilter;

	public WebSecurityConfig(UserDetailsService userDetailsService,
			JwtAuthenticationFilter jwtAuthenticationFilter,
			UserIdHeaderFilter userIdHeaderFilter) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userIdHeaderFilter = userIdHeaderFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						request -> request
								.requestMatchers("api/users/register", "api/users/login")
								.permitAll()
								.anyRequest()
								.authenticated()
				)
				.httpBasic(Customizer.withDefaults())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(userIdHeaderFilter, JwtAuthenticationFilter.class);

		return httpSecurity.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(14);
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		return provider;
	}

	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration
	) throws Exception {
		return configuration.getAuthenticationManager();
	}
}