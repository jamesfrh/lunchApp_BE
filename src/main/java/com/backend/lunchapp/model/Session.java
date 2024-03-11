package com.backend.lunchapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int sessionCode;
    
    private String selectedRestaurant;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

	public Session() {
		super();
	}

	public Session(String username, int sessionCode, String selectedRestaurant,LocalDateTime startTime, LocalDateTime endTime) {
		super();
		this.username = username;
		this.sessionCode = sessionCode;
		this.selectedRestaurant = selectedRestaurant;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getUsername() {
		return username;
	}

	public String getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(String selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(int sessionCode) {
		this.sessionCode = sessionCode;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
