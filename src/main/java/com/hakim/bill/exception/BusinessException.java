package com.hakim.bill.exception;

import com.hakim.bill.common.ResultStatus;

public abstract class BusinessException extends Exception {

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public abstract ResultStatus getResultStatus();

}
