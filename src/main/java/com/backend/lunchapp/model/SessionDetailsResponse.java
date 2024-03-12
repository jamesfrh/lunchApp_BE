package com.backend.lunchapp.model;

import java.util.List;

public class SessionDetailsResponse {
    private Session sessionDetails;
    private List<Restaurant> restaurantList;

    public SessionDetailsResponse(Session sessionDetails, List<Restaurant> restaurantList) {
        this.sessionDetails = sessionDetails;
        this.restaurantList = restaurantList;
    }
    
	public Session getSessionDetails() {
		return sessionDetails;
	}

	public void setSessionDetails(Session sessionDetails) {
		this.sessionDetails = sessionDetails;
	}

	public List<Restaurant> getRestaurantList() {
		return restaurantList;
	}

	public void setRestaurantList(List<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}
    
}
