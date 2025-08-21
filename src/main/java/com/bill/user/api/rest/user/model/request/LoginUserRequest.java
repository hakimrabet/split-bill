package com.bill.user.api.rest.user.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoginUserRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
