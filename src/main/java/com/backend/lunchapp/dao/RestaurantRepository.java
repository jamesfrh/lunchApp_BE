package com.backend.lunchapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.lunchapp.model.Restaurant;

@Repository

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
    List<Restaurant> findByUsernameAndSessionCode(String username, int sessionCode);
    List<Restaurant> findBySessionCode(int sessionCode);
    boolean existsByNameAndSessionCode(String restaurantName, int sessionCode);
    Restaurant findBySessionCodeAndUsername(int sessionCode, String username);

}
