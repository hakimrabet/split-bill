package com.bill.user.api.rest.user.model.response;

import com.bill.user.common.ResponseService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserResponse extends ResponseService {

	private UserDto user;

}
