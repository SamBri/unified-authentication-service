package com.nothing.security.exceptions;


import java.time.ZonedDateTime;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import com.nothing.security.response.RootResponse;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class UnifiedAuthenticationServiceExceptionService {
	
	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<RootResponse<String>> handleUserCreationExceptionEvent(UserCreationException  e){
		
		log.info("@@  handleUserCreationExceptionEvents handler");
		
		log.error("UserCreationException",e);
		
		RootResponse<String> exceptionResponse = new RootResponse<>();
		
		exceptionResponse.setCode(HttpStatus.EXPECTATION_FAILED.value());
		exceptionResponse.setMessage(e.getMessage());
		exceptionResponse.setStatus("error");
		exceptionResponse.setResponse(null);
		return new ResponseEntity<RootResponse<String>>(exceptionResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<RootResponse<String>> handleUserNotFoundExceptionEvent(UserNotFoundException e){
		
		log.info("@@  handleUserNotFoundExceptionEvent handler");
		
		log.error("UserNotFoundException",e);
		
		RootResponse<String> exceptionResponse = new RootResponse<>();
		
		exceptionResponse.setCode(HttpStatus.EXPECTATION_FAILED.value());
		exceptionResponse.setMessage(e.getMessage());
		exceptionResponse.setStatus("error");
		exceptionResponse.setResponse(null);
		return new ResponseEntity<RootResponse<String>>(exceptionResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
	
	
	@ExceptionHandler(IncorrectUserPasswordException.class)
	public ResponseEntity<RootResponse<String>> handleIncorrectUserPasswordEvent(IncorrectUserPasswordException e){
		
		log.info("@@  handleIncorrectUserPassword handler");
		
		log.error("IncorrectUserPasswordException",e);
		
		RootResponse<String> exceptionResponse = new RootResponse<>();
		
		exceptionResponse.setCode(HttpStatus.EXPECTATION_FAILED.value());
		exceptionResponse.setMessage(e.getMessage());
		exceptionResponse.setStatus("error");
		exceptionResponse.setResponse(null);
		return new ResponseEntity<RootResponse<String>>(exceptionResponse,HttpStatus.NOT_ACCEPTABLE);
	}

}
