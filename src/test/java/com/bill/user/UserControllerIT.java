package com.bill.user;

import com.bill.user.api.rest.user.model.request.RegisterUserRequest;
import com.bill.user.api.rest.user.model.response.UserResponse;
import com.bill.user.model.user.dao.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT extends AbstractContainerBaseTest {

	@Autowired
	private UserDao userDao;

	@BeforeEach
	void setUp() {
		userDao.deleteAll();
	}

	@Test
	void whenCreateUser_thenReturns201() {
		RegisterUserRequest request = new RegisterUserRequest();
		request.setUsername("test@example.com");
		request.setName("Test User");
		request.setPassword("password123");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(request, headers);

		ResponseEntity<UserResponse> response = restTemplate.exchange("/api/users/register", HttpMethod.POST,
				entity, UserResponse.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("test@example.com", response.getBody()
				.getUser()
				.getUsername());
		assertEquals("Test User", response.getBody()
				.getUser()
				.getName());
		assertFalse(response.getBody()
				.getUser()
				.getName()
				.isEmpty(), "User Id should not be empty after save");
	}

}