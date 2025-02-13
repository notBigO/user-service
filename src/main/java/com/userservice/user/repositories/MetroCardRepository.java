package com.userservice.user.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userservice.user.entities.MetroCard;
import com.userservice.user.entities.User;

@Repository
public interface MetroCardRepository extends JpaRepository<MetroCard, Long> {
    Optional<MetroCard> findByCardNumber(String cardNumber);

    List<MetroCard> findByUser(User user);
}
