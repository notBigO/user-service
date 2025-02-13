package com.userservice.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.user.entities.TravelHistory;
import com.userservice.user.entities.User;

@Repository
public interface TravelHistoryRepository extends JpaRepository<TravelHistory, Long> {
    List<TravelHistory> findTop10ByUserOrderByTravelDateTimeDesc(User user);
}
