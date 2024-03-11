package com.backend.lunchapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.lunchapp.service.AuthService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class AuthController {
	
    @Autowired
    private AuthService authService;

    @GetMapping(path = "/api/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        // Perform authentication logic
        if (authService.isValidCredentials(username)) {
        	return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Login failed\"}");
        }
    }

}