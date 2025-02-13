package com.userservice.user.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.user.entities.TravelHistory;
import com.userservice.user.entities.User;
import com.userservice.user.repositories.TravelHistoryRepository;
import com.userservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelHistoryService {
    private final TravelHistoryRepository travelHistoryRepository;
    private final UserRepository userRepository;

    public List<TravelHistory> getLast10Trips(String userId) throws Exception {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("User not found"));

        return travelHistoryRepository.findTop10ByUserOrderByTravelDateTimeDesc(user);
    }
}
