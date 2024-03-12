package com.backend.lunchapp.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.backend.lunchapp.model.SessionRequest;
import com.backend.lunchapp.model.Session;
import com.backend.lunchapp.model.SessionDetailsResponse;
import com.backend.lunchapp.service.SessionService;
import com.backend.lunchapp.service.RestaurantService;

import jakarta.websocket.SessionException;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/sessions")
public class SessionController {
	
    @Autowired
    private SessionService sessionService;
    @Autowired
    private RestaurantService restaurentService;

    
    @GetMapping("/check-active/{username}")
    public ResponseEntity<Map<String, Object>> checkActiveSession(@PathVariable String username) {
        Session activeSession = sessionService.getActiveSession(username);
        Map<String, Object> response = new HashMap<>();
        if (activeSession != null) {
            response.put("hasActiveSession", true);
            response.put("sessionCode", activeSession.getSessionCode());
        } else {
            response.put("hasActiveSession", false);
        }

        return ResponseEntity.ok(response);
    }
    @PostMapping("/initiate/{username}")
    public ResponseEntity<?> initiateSession(@PathVariable String username) {
        try {
            int generatedSessionCode = sessionService.initiateSession(username);
            return ResponseEntity.ok(Map.of("generatedSessionCode", generatedSessionCode));
        } catch (SessionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/session/{sessionCode}")
    public ResponseEntity<?> checkActiveSession(@PathVariable int sessionCode) {
        try {
        	SessionDetailsResponse  sessionDetails = sessionService.getSession(sessionCode);

            if (sessionDetails != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("sessionDetails", sessionDetails); 

                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "No session found for the provided session code."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking active session: " + e.getMessage());
        }
    }
   
    @PostMapping("/join-session/{username}/{sessionCode}")
    public ResponseEntity<?> joinSession(@PathVariable String username,
    	    @PathVariable int sessionCode) {
    	System.out.println("in join api");
        if (sessionService.joinSession(username, sessionCode)) {
            return ResponseEntity.ok(Map.of("message", "Successfully joined the session"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid session code or unable to join the session"));
        }
    }
    
    @PostMapping("/end-session")
    public ResponseEntity<?> endSession(@RequestBody SessionRequest endSessionRequest) {
    	System.out.println("in session controller");
        String username = endSessionRequest.getUsername().toLowerCase();
        int sessionCode = endSessionRequest.getSessionCode();
    	System.out.println("username is " + username);
    	System.out.println("sessionCode is " + sessionCode);

        try {
            boolean endedSession = sessionService.endSession(username, sessionCode);
            if (endedSession) {
            	  // Retrieve all restaurant names for the given session
                List<String> restaurantNames = restaurentService.getRestaurantNamesBySessionCode(sessionCode);
                if(restaurantNames.isEmpty()) {
                    return ResponseEntity.ok(Map.of(
                            "message", "Successfully ended the session.",
                            "selectedRestaurant", "No submissions found for the session",
                            "sessionCode", sessionCode
                    ));  
                }
  
     
                String selectedRestaurant = getRandomRestaurantName(restaurantNames);
                sessionService.saveSelectedRestaurantToSession(sessionCode, selectedRestaurant);

                // Return the result to the front end
                return ResponseEntity.ok(Map.of(
                        "message", "Successfully ended the session.",
                        "selectedRestaurant", selectedRestaurant,
                        "sessionCode", sessionCode
                ));            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid session code or unable to end the session."));
            }
        } catch (Exception e) {
            // Handle exceptions, log the error, etc.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error ending the session: " + e.getMessage());
        }
        
    }
    
    private String getRandomRestaurantName(List<String> restaurantNames) {
        if (restaurantNames != null && !restaurantNames.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(restaurantNames.size());
            return restaurantNames.get(randomIndex);
        }
        return null;
    }
    
    @GetMapping("/check-active-session/{username}/{sessionCode}")
    public ResponseEntity<?> checkActiveSessionWithSessionCode(
            @PathVariable String username,
            @PathVariable int sessionCode) {

        try {
            boolean isActiveSession = sessionService.checkActiveSession(username, sessionCode);

            if (isActiveSession) {
                return ResponseEntity.ok(Map.of("message", "Active session found."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "No active session found for the provided username and session code."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error checking active session: " + e.getMessage());
        }
    }


}
