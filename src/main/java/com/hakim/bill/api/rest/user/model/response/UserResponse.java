package com.hakim.bill.api.rest.user.model.response;

import com.hakim.bill.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserResponse extends ResponseService {

	private UserDto user;

}
