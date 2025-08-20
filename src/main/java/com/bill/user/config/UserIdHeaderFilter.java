package com.bill.user.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.bill.user.service.auth.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class UserIdHeaderFilter extends OncePerRequestFilter {

	@Autowired
	private SecurityUtils securityUtils;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			try {
				Long userId = securityUtils.getCurrentUserId();
				UserIdRequestWrapper wrappedRequest = new UserIdRequestWrapper(request, userId);
				filterChain.doFilter(wrappedRequest, response);
				return;

			} catch (Exception e) {
				System.out.println("Could not extract user ID: " + e.getMessage());
			}
		}

		filterChain.doFilter(request, response);
	}

	private static class UserIdRequestWrapper extends HttpServletRequestWrapper {
		private final Long userId;

		public UserIdRequestWrapper(HttpServletRequest request, Long userId) {
			super(request);
			this.userId = userId;
		}

		@Override
		public String getHeader(String name) {
			if ("X-User-Id".equalsIgnoreCase(name)) {
				return String.valueOf(userId);
			}
			return super.getHeader(name);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			if ("X-User-Id".equalsIgnoreCase(name)) {
				return Collections.enumeration(Collections.singletonList(String.valueOf(userId)));
			}
			return super.getHeaders(name);
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			names.add("X-User-Id");
			return Collections.enumeration(names);
		}
	}
}