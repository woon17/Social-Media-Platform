package com.dxc.smp.requestValidationConfig;

import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {
	   private String message;
	   private HttpStatus status;
	   private List<String> error;
	public ApiError(HttpStatus badRequest,  String messgae, List<String> errors) {
		super();
		this.message = messgae;
		this.status = badRequest;
		this.error = errors;
	}
	public HttpStatus getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

}
