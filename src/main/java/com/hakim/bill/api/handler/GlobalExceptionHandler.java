package com.hakim.bill.api.handler;

import java.security.InvalidParameterException;

import com.hakim.bill.common.GeneralResponse;
import com.hakim.bill.common.ResponseService;
import com.hakim.bill.common.ResultStatus;
import com.hakim.bill.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final Environment environment;

	@ExceptionHandler(BusinessException.class)
	public final ResponseEntity<ResponseService> handleBusinessException(BusinessException ex, WebRequest request) {
		logger.error("business exception occurred", ex);
		return ResponseEntity.unprocessableEntity().body(new GeneralResponse(ex.getResultStatus()));
	}

	@ExceptionHandler(InvalidParameterException.class)
	public final ResponseEntity<ResponseService> handleInvalidParameterException(InvalidParameterException ex) {
		logger.error("invalid param error", ex);
		return ResponseEntity.unprocessableEntity().body(new GeneralResponse(ResultStatus.INVALID_PARAMETER));
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public final ResponseEntity<ResponseService> handleUnsupportedOperationException(UnsupportedOperationException ex) {
		logger.error(ResultStatus.FORBIDDEN_REQUEST.getDescription(), ex);
		return new ResponseEntity<>(new GeneralResponse(ResultStatus.FORBIDDEN_REQUEST), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ResponseService> HandleConstraintViolationException(ConstraintViolationException ex) {
		logger.warn("constraint violation exception ", ex);
		var response = new GeneralResponse(ResultStatus.INVALID_PARAMETER);
		ex.getConstraintViolations()
			.stream()
			.findFirst()
			.ifPresent(violation -> response.setResult(ResultStatus.INVALID_PARAMETER,
					environment.getProperty(violation.getMessage())));
		return ResponseEntity.unprocessableEntity().body(response);
	}

	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<ResponseService> handleGeneralException(Throwable throwable) {
		logger.error(ResultStatus.UNKNOWN.getDescription(), throwable);
		return new ResponseEntity<>(new GeneralResponse(ResultStatus.UNKNOWN), HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
