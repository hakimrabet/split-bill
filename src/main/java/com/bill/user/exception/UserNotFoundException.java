package com.bill.user.exception;

import com.bill.user.common.ResultStatus;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(String message) {
		super(message);
	}

	@Override
	public ResultStatus getResultStatus() {
		return ResultStatus.USER_NOT_FOUND;
	}

}