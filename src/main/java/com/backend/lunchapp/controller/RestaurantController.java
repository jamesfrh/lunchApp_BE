package com.backend.lunchapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.lunchapp.model.Restaurant;
import com.backend.lunchapp.service.RestaurantService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/user/{username}/session/{sessionCode}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByUserAndSession(
            @PathVariable String username,
            @PathVariable int sessionCode) {
        List<Restaurant> restaurants = restaurantService.getRestaurantsByUserAndSession(username, sessionCode);
        return ResponseEntity.ok(restaurants);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitRestaurant(@RequestBody Restaurant restaurantRequest) {
        try {
            int sessionCode = restaurantRequest.getSessionCode();
            String username = restaurantRequest.getUsername();
            String restaurantName = restaurantRequest.getRestaurantName();
            // Check if the restaurant name already exists in the same session
            if (restaurantService.isDuplicateNameInSession(restaurantRequest.getRestaurantName(), sessionCode)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Restaurant with the same name already exists in this session.");
            }
            // Check if the restaurant with the same session code and username exists
//            Restaurant existingRestaurant = restaurantService.findBySessionCodeAndUsername(sessionCode, username);
//            if (existingRestaurant != null) {
//                // Restaurant with the same session code and username exists, update the restaurant name
//                existingRestaurant.setRestaurantName(restaurantName);
//                restaurantService.submitRestaurant(existingRestaurant);
//                System.out.println("Restaurant name updated");
//                return ResponseEntity.ok(Map.of("message", "Restaurant name updated successfully."));
//            }
//            else {
            if(restaurantService.submitRestaurant(restaurantRequest)) {
            	System.out.println("submitted");
                return ResponseEntity.ok(Map.of("message", "Restaurant submitted successfully."));
                }
            else {
                	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit restaurant");
                	}
//            }
        }catch (Exception e){
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit restaurant: " + e.getMessage());
        	}
        }
    
    @GetMapping("/submitted-restaurants/{sessionCode}")
    public ResponseEntity<List<Restaurant>> getSubmittedRestaurants(@PathVariable int sessionCode) {
        List<Restaurant> submittedRestaurants = restaurantService.getSubmittedRestaurants(sessionCode);
        return ResponseEntity.ok(submittedRestaurants);
    }
    }
