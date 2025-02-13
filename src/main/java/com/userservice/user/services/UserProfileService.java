package com.userservice.user.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.userservice.user.entities.TravelHistory;
import com.userservice.user.entities.UserProfile;
import com.userservice.user.repositories.UserProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final TravelHistoryService travelHistoryService;

    public UserProfile getUserProfile(String userId) throws Exception {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public Map<String, Object> getUserProfileWithHistory(String userId) throws Exception {
        UserProfile profile = getUserProfile(userId);
        List<TravelHistory> travelHistory = travelHistoryService.getLast10Trips(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("profile", profile);
        response.put("travelHistory", travelHistory);
        return response;
    }
}
