package com.learningtech.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learningtech.model.Users;
import com.learningtech.repository.UsersRepository;

@Service
public class UsersService {
	
	@Autowired
	private UsersRepository userRepo;
	
//	public Users saveUser(Users user) {
//		user = userRepo.save(user);
//		return user;
//	}

	public Users getUserByUserName(String userName) {
		Optional<Users> opt = userRepo.findByUserName(userName);
		if(opt.isPresent()) {
			return opt.get();
		}
		return null;
	}
}
