package com.learningtech.genericmodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String firstName;
	private String lastName;
	private String userEmail;
	private String userName;
	private String password;
}
