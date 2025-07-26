package com.inkognito.service;

import com.inkognito.model.User;
import com.inkognito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User createUser(String username, String professionalJob, String hobbyJob) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = new User(username);
        user.setProfessionalJob(professionalJob);
        user.setHobbyJob(hobbyJob);
        return userRepository.save(user);
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User updateDailyFeeling(String username, String dailyFeeling, String lifeSituation) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setDailyFeeling(dailyFeeling);
        user.setLifeSituation(lifeSituation);
        return userRepository.save(user);
    }
    
    public User updateUserProfile(String username, String professionalJob, String hobbyJob) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        user.setProfessionalJob(professionalJob);
        user.setHobbyJob(hobbyJob);
        return userRepository.save(user);
    }
    
    public boolean canUpdateFeeling(User user) {
        if (user.getFeelingUpdatedAt() == null) {
            return true;
        }
        return user.getFeelingUpdatedAt().isBefore(LocalDateTime.now().minusHours(24));
    }
    
    // Scheduled task to clear daily feelings every 24 hours
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void clearOldFeelings() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        int cleared = userRepository.clearOldFeelings(cutoffTime);
        if (cleared > 0) {
            System.out.println("Cleared daily feelings for " + cleared + " users");
        }
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
}