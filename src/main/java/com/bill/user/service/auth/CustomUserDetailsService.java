package com.bill.user.service.auth;

import java.util.Objects;

import com.bill.user.model.user.User;
import com.bill.user.model.user.dao.UserDao;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if (Objects.isNull(user)) {
			System.out.println("User not available");
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}

}
