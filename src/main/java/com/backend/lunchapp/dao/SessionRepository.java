package com.backend.lunchapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.lunchapp.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long>{
    Optional<Session> findByUsernameAndEndTimeIsNull(String username);
    Optional<Session> findBySessionCodeAndEndTimeIsNull(Integer sessionCode);
    Optional<Session> findByUsernameAndSessionCodeAndEndTimeIsNull(String username, int sessionCode);
    Optional<Session> findBySessionCode(int sessionCode);


}
