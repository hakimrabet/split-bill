package com.bill.user.service.auth;

import java.util.Objects;

import com.bill.user.model.user.User;
import com.bill.user.model.user.dao.UserDao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDao userRepository;

	public CustomUserDetailsService(UserDao userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (Objects.isNull(user)) {
			System.out.println("User not available");
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
}
