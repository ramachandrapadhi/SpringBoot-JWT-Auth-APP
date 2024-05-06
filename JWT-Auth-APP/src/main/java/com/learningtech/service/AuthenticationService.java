package com.learningtech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.learningtech.config.JWTService;
import com.learningtech.genericmodel.AuthenticationRequest;
import com.learningtech.genericmodel.AuthenticationResponse;
import com.learningtech.genericmodel.RegisterRequest;
import com.learningtech.model.Role;
import com.learningtech.model.Users;
import com.learningtech.repository.UsersRepository;

@Service
public class AuthenticationService {

	@Autowired
	private UsersRepository repo;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		Users user = Users.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.userEmail(request.getUserEmail())
				.userName(request.getUserName())
				.password(encoder.encode(request.getPassword()))
				.role(Role.ADMIN)
				.build();
		repo.save(user);
		String token = jwtService.generateToken(user);
//			TODO send mail for user Got registered 
		return AuthenticationResponse.builder().token(token).tokenExpiryIn(jwtService.getExpiryTime(token)+" minuts").build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		Users user = repo.findByUserName(authRequest.getUserName())
				.orElseThrow(() -> new RuntimeException("User is not valid"));

		String token = jwtService.generateToken(user);
	
		return AuthenticationResponse.builder().token(token).tokenExpiryIn(jwtService.getExpiryTime(token)+" minuts").build();
	}

	public Object resetPasswordMailVerify(String emilId) {
		
		return null;
	}

	public Object resetpasswordVerifyOTP(String emilId) {
		
		return null;
	}

	public Object resetPasswordMailVerify(RegisterRequest request) {
		
		return null;
	}
	
	
	

}
