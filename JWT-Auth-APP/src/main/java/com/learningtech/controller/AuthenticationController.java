package com.learningtech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learningtech.genericmodel.AuthenticationRequest;
import com.learningtech.genericmodel.AuthenticationResponse;
import com.learningtech.genericmodel.RegisterRequest;
import com.learningtech.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request){
		return new ResponseEntity<>(service.register(request), HttpStatus.OK);	
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest authRequest){
		return new ResponseEntity<>(service.authenticate(authRequest), HttpStatus.OK);	
	}
	
	@PostMapping("/resetpasswordVerify")
	public ResponseEntity<Object> resetPasswordVerify(@RequestParam String emilId){
		return new ResponseEntity<>(service.resetPasswordMailVerify(emilId), HttpStatus.OK);	
	}
	
	@PostMapping("/resetpasswordVerifyOTP")
	public ResponseEntity<Object> resetpasswordVerifyOTP(@RequestParam String emilId){
		return new ResponseEntity<>(service.resetpasswordVerifyOTP(emilId), HttpStatus.OK);	
	}
	
	@PostMapping("/resetpassword")
	public ResponseEntity<Object> resetPasswordMailVerify(@RequestParam RegisterRequest request){
		return new ResponseEntity<>(service.resetPasswordMailVerify(request), HttpStatus.OK);	
	}

}
