package com.userservice.user.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.userservice.user.entities.TravelHistory;
import com.userservice.user.repositories.TravelHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelHistoryService {
    private final TravelHistoryRepository travelHistoryRepository;

    public List<TravelHistory> getLast10Trips(String userId) {
        return travelHistoryRepository.findTop10ByUserIdOrderByCheckOutTimeDesc(userId);
    }
}
