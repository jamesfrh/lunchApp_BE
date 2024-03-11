package com.backend.lunchapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class User {
	@Id
	@SequenceGenerator(
			name= "user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence"
			)
	private Long id;
	private String username;
	private String name;
	
	public User() {
	}
	

	public User(String username, String name) {
		super();
		this.username = username;
		this.name = name;
	}
	public String getUsername() {
		return username;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	@Override
	public String toString() {
		return "User [username=" + username +"]";
	}

	
	
}
