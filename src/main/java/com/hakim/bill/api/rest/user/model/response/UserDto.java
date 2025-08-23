package com.hakim.bill.api.rest.user.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

	private Long id;

	private String name;

	private String username;

}
