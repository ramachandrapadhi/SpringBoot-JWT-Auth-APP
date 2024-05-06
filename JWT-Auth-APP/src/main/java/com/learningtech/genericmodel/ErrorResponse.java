package com.learningtech.genericmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
	  private Boolean status;
	  private Integer statusCode;
	  private String message;
}
