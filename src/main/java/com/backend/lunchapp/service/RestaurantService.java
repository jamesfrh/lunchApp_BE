package com.backend.lunchapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.lunchapp.dao.RestaurantRepository;
import com.backend.lunchapp.exceptions.SessionInactiveException;
import com.backend.lunchapp.model.Restaurant;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private SessionService sessionService;

    public List<Restaurant> getRestaurantsByUserAndSession(String username, int sessionCode) {
        return restaurantRepository.findByUsernameAndSessionCode(username, sessionCode);
    }
    public List<String> getRestaurantNamesBySessionCode(int sessionCode) {
        List<Restaurant> restaurants = restaurantRepository.findBySessionCode(sessionCode);
        return restaurants.stream()
                .map(Restaurant::getRestaurantName)
                .collect(Collectors.toList());
    }


    public Restaurant saveRestaurant(String username, int sessionCode, String restaurantName) {
        Restaurant restaurant = new Restaurant();
        restaurant.setUsername(username);
        restaurant.setSessionCode(sessionCode);
        restaurant.setRestaurantName(restaurantName);
        return restaurantRepository.save(restaurant);
    }
    
    public boolean submitRestaurant(Restaurant restaurantRequest) {
    	System.out.println("in submit res service");
    	System.out.println(restaurantRequest.getRestaurantName());
    	System.out.println(restaurantRequest.getSessionCode());
    	System.out.println(restaurantRequest.getUsername());

    	try {
//            if (!sessionService.isSessionActive(restaurantRequest.getUsername(), restaurantRequest.getSessionCode())) {
//                throw new SessionInactiveException("Session is not active.");
//            }
        	System.out.println("active sessions");
            Restaurant restaurant = new Restaurant();
            restaurant.setUsername(restaurantRequest.getUsername());
            restaurant.setSessionCode(restaurantRequest.getSessionCode());
            restaurant.setRestaurantName(restaurantRequest.getRestaurantName());
        	System.out.println("saving restaurant");

            restaurantRepository.save(restaurant);
            return true;
    	}catch (SessionInactiveException e) {
            return false;
    	}
    }
    public boolean isDuplicateNameInSession(String restaurantName, int sessionCode) {
        // Check if a restaurant with the same name exists in the same session
        return restaurantRepository.existsByNameAndSessionCode(restaurantName, sessionCode);
    }
    public Restaurant findBySessionCodeAndUsername(int sessionCode, String username) {
        return restaurantRepository.findBySessionCodeAndUsername(sessionCode, username);
    }
    public List<Restaurant> getSubmittedRestaurants(int sessionCode) {

        return restaurantRepository.findBySessionCode(sessionCode);

    }

}
