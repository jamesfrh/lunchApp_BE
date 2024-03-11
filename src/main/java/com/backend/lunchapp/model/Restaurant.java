package com.backend.lunchapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int sessionCode;
    private String name;
    
	public Restaurant() {
		super();
	}

	public Restaurant(String username, int sessionCode, String restaurantName) {
		super();
		this.username = username;
		this.sessionCode = sessionCode;
		this.name = restaurantName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getSessionCode() {
		return sessionCode;
	}
	public void setSessionCode(int sessionCode) {
		this.sessionCode = sessionCode;
	}
	public String getRestaurantName() {
		return name;
	}
	public void setRestaurantName(String restaurantName) {
		this.name = restaurantName;
	}
    
    

}
