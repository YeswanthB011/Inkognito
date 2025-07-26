package com.inkognito.repository;

import com.inkognito.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.feelingUpdatedAt < :cutoffTime")
    Iterable<User> findUsersWithOldFeelings(@Param("cutoffTime") LocalDateTime cutoffTime);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.dailyFeeling = null, u.lifeSituation = null, u.feelingUpdatedAt = null WHERE u.feelingUpdatedAt < :cutoffTime")
    int clearOldFeelings(@Param("cutoffTime") LocalDateTime cutoffTime);
}