package com.hakim.bill.service.user.impl;

import java.util.stream.Collectors;

import com.hakim.bill.exception.UserAlreadyExistsException;
import com.hakim.bill.exception.UserNotFoundException;
import com.hakim.bill.model.user.User;
import com.hakim.bill.model.user.dao.UserDao;
import com.hakim.bill.service.auth.CustomUserDetails;
import com.hakim.bill.service.auth.JwtService;
import com.hakim.bill.service.user.UserService;
import com.hakim.bill.service.user.mapper.UserServiceMapper;
import com.hakim.bill.service.user.model.AddUserModel;
import com.hakim.bill.service.user.model.LoginModel;
import com.hakim.bill.service.user.model.UserResult;
import com.hakim.bill.service.user.model.UsersResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;

	private final UserServiceMapper mapper;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	@Override
	public UserResult registerUser(AddUserModel model) throws UserAlreadyExistsException {
		if (userDao.existsByUsername(model.getUsername())) {
			throw new UserAlreadyExistsException("Username " + model.getUsername() + " already exists");
		}
		var user = mapper.toUser(model, bCryptPasswordEncoder.encode(model.getPassword()));
		User savedUser = userDao.save(user);
		return mapper.toUserResult(savedUser);
	}

	@Override
	public UserResult getUserById(Long userId) throws UserNotFoundException {
		User user = userDao.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		return mapper.toUserResult(user);
	}

	@Override
	public UsersResult getAllUsers() {
		return mapper.toUsersResult(userDao.findAll()
				.stream()
				.map(mapper::toUserInfo)
				.collect(Collectors.toList()));
	}

	@Override
	public String login(LoginModel model) {
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));

		if (authenticate.isAuthenticated()) {
			CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
			return jwtService.generateToken(userDetails);
		}

		return "failure";
	}

}
