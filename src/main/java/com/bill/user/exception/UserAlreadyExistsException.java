package com.bill.user.exception;


import com.bill.user.common.ResultStatus;

public class UserAlreadyExistsException extends BusinessException {

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	@Override
	public ResultStatus getResultStatus() {
		return ResultStatus.USER_DUPLICATE_USERNAME;
	}
}
