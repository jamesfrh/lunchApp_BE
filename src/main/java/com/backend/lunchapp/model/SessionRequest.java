package com.backend.lunchapp.model;

public class SessionRequest {
	
    public SessionRequest(String username, int sessionCode) {
		super();
		this.username = username;
		this.sessionCode = sessionCode;
	}
	private String username;
    private int sessionCode;
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

}