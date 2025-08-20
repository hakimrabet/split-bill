package com.bill.user.api.rest.user;


import com.bill.user.api.rest.user.mapper.UserResourceMapper;
import com.bill.user.api.rest.user.model.request.LoginUserRequest;
import com.bill.user.api.rest.user.model.request.RegisterUserRequest;
import com.bill.user.api.rest.user.model.response.UserResponse;
import com.bill.user.api.rest.user.model.response.UsersResponse;
import com.bill.user.exception.UserAlreadyExistsException;
import com.bill.user.exception.UserNotFoundException;
import com.bill.user.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

	private final UserService service;

	private final UserResourceMapper mapper;

	@PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserResponse registerUser(@Valid @RequestBody RegisterUserRequest request) throws UserAlreadyExistsException {
		var result = service.registerUser(mapper.toAddUserModel(request));
		return mapper.toUserResponse(result);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginUserRequest request) {
		return service.login(mapper.toLoginModel(request));
	}

	@GetMapping("/greet")
	public String greet(@RequestHeader(value = "X-User-Id", required = false) Long userId) {
		if (userId != null) {
			return "Welcome! Your user ID from header is: " + userId;
		}
		return "Welcome! User ID not available";
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserResponse getUserById(@PathVariable Long id) throws UserNotFoundException {
		return mapper.toUserResponse(service.getUserById(id));
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public UsersResponse getAllUsers() {
		return mapper.toUsersResponse(service.getAllUsers());
	}
}
