package com.userservice.user.services;

import org.springframework.stereotype.Service;

import com.userservice.user.entities.User;
import com.userservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public User createUser(User user) {
        log.info("Creating user: {}", user);
        return userRepository.save(user);
    }
}