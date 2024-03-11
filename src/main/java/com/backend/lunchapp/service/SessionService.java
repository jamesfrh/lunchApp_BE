package com.backend.lunchapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.backend.lunchapp.dao.SessionRepository;
import com.backend.lunchapp.dao.UserRepository;
import com.backend.lunchapp.model.Session;
import com.backend.lunchapp.model.User;

import jakarta.websocket.SessionException;


@Service
public class SessionService {
	
    @Autowired
    private SessionRepository sessionRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;

    public int generateRandomSessionCode() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }
    
    public boolean hasActiveSession(String username) {
        Optional<Session> activeSession = sessionRepository.findByUsernameAndEndTimeIsNull(username);
        return activeSession.isPresent();
    }
    public Session getActiveSession(String username) {
        Optional<Session> activeSession = sessionRepository.findByUsernameAndEndTimeIsNull(username);
        
        return activeSession.orElse(null);
    }
    public Session getActiveSession(int sessionCode) {
        // Assuming that the Session entity has a field 'endTime' to determine if the session is active
        Optional<Session> sessionDetails = sessionRepository.findBySessionCode(sessionCode);
        
        return sessionDetails.orElse(null);
    }
    public int initiateSession(String username) throws SessionException {
        int generatedSessionCode = generateRandomSessionCode();
        sessionRepository.save(new Session(username, generatedSessionCode, null,LocalDateTime.now(), null));
        
        // Send email notification to users
        sendSessionCodeEmail(username, generatedSessionCode);
        return generatedSessionCode;
    }
    public boolean joinSession(String username, int sessionCode) {
        if (!isValidSessionCode(sessionCode)) {
            return false;
        }
        // Return the generated session code
        System.out.println("true");
        return true;
    }
    private boolean isValidSessionCode(int sessionCode) {
        // Check if the session code exists in the database and has no end date
        Optional<Session> sessionOptional = sessionRepository.findBySessionCodeAndEndTimeIsNull(sessionCode);
        return sessionOptional.isPresent();
    }
    
    public boolean endSession(String username, int sessionCode) {
        try {
            Optional<Session> optionalSession = sessionRepository.findByUsernameAndSessionCodeAndEndTimeIsNull(username, sessionCode);

            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();
                session.setEndTime(LocalDateTime.now());
                sessionRepository.save(session);
                return true; 
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return false;
        }
    }
    public void saveSelectedRestaurantToSession(int sessionCode, String selectedRestaurant) {
        // Retrieve the session by session code
    	Optional<Session> optionalSession = sessionRepository.findBySessionCode(sessionCode);

        if (optionalSession.isPresent()) {
            // Extract the Session object from the Optional
            Session session = optionalSession.get();

            // Update the selected restaurant in the session
            session.setSelectedRestaurant(selectedRestaurant);

            // Save the updated session entity with the selected restaurant
            sessionRepository.save(session);
        }
        }
    private void sendSessionCodeEmail(String username, int sessionCode) {

        SimpleMailMessage message = new SimpleMailMessage();
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<User> users = userRepository.findAll();
        for(User user : users) {
            if (user.getUsername().equals(username)) {
                continue;
            }
            message.setTo(user.getUsername());  // Replace with the actual user's email address
            message.setSubject("Lunch Session Code for " + todayDate.toString());
            message.setText("Dear " + user.getName() + ",\n\nA new session code has been generated: " + sessionCode + 
            		" by " + username + ".\n\nPlease submit your restauraunt by 12pm. \n\n"
            				+ "This is a system generated email. Please do not reply to this message.");
            javaMailSender.send(message);
        }

    }
    public boolean checkActiveSession(String username, int sessionCode) {
        // Assuming you have a Session entity with attributes 'username', 'sessionCode', and 'isActive'
        Optional<Session> optionalSession = sessionRepository.findByUsernameAndSessionCodeAndEndTimeIsNull(username, sessionCode);
        return optionalSession.isPresent();


    }


}
