package com.backend.lunchapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lunchapp.dao.UserRepository;
import com.backend.lunchapp.model.User;

@Service
public class AuthService {
	
    @Autowired
    private  UserRepository userRepository;


    public boolean isValidCredentials(String username) {
        // Implement your authentication logic here
        // Check the username and password against your database
        Optional<User> userOptional = userRepository.findByUsername(username);
        System.out.println(userOptional + "HERE");

        return userOptional.isPresent();
    }

}
