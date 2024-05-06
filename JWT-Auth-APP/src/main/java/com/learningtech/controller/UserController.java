package com.learningtech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@GetMapping("/get")
	public ResponseEntity<String> get(){
		return ResponseEntity.ok("Hi from UserController");
	}

}
