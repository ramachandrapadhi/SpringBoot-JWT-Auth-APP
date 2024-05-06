package com.learningtech.genericmodel;

import lombok.Data;

@Data
public class GenericResponse {
	private Boolean status;
	private Integer statusCode;
	private String message;
	private Object data;
}
