package com.bill.user.api.rest.user.model.request;

import com.bill.user.common.RequestService;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterUserRequest extends RequestService {

	@NotBlank
	private String name;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
