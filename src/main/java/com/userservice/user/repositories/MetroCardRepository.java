package com.userservice.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.user.entities.MetroCard;

@Repository
public interface MetroCardRepository extends JpaRepository<MetroCard, Long> {
    Optional<MetroCard> findByUserId(String userId);
}
