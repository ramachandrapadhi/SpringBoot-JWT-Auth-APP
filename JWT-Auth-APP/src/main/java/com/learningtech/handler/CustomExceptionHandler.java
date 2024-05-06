package com.learningtech.handler;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learningtech.genericmodel.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;


@RestControllerAdvice
public class CustomExceptionHandler {
		
	@ExceptionHandler({BadCredentialsException.class,ExpiredJwtException.class})
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
		return new ResponseEntity<>(
				ErrorResponse.builder()
				.message(e.getMessage())
				.statusCode(HttpStatus.UNAUTHORIZED.value())
				.status(Boolean.FALSE).build(),
				HttpStatus.UNAUTHORIZED);

	}
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ErrorResponse> handleUserInputException(SQLException e) {
		return new ResponseEntity<>(
				ErrorResponse.builder()
				.message(e.getLocalizedMessage())
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.status(Boolean.FALSE).build(),
				HttpStatus.BAD_REQUEST);
		
	}
}
