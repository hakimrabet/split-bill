package com.bill.user.service.auth;

import com.bill.user.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

	@Autowired
	private JwtService jwtService;

	public Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
		}
		if (authentication != null && authentication.getCredentials() instanceof String) {
			String token = (String) authentication.getCredentials();
			return jwtService.extractUserId(token);
		}

		throw new IllegalStateException("User ID not available");
	}
}