package com.userservice.user.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.user.entities.MetroCard;

@Repository
public interface MetroCardRepository extends JpaRepository<MetroCard, Long> {
    List<MetroCard> findByUserId(Long userId);

    Optional<MetroCard> findByIdAndUserId(Long cardId, Long userId);

    Optional<MetroCard> findByCardNumber(String cardNumber);

    boolean existsByCardNumber(String cardNumber);
}