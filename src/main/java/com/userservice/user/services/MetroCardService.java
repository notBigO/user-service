package com.userservice.user.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.userservice.user.entities.MetroCard;
import com.userservice.user.entities.User;
import com.userservice.user.repositories.MetroCardRepository;
import com.userservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetroCardService {
    private final MetroCardRepository metroCardRepository;
    private final UserRepository userRepository;

    public MetroCard buyMetroCard(String userId, double balance) throws Exception {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("User not found"));

        MetroCard metroCard = new MetroCard();
        metroCard.setCardNumber(UUID.randomUUID().toString());
        metroCard.setBalance(balance);
        metroCard.setUser(user);

        log.info("Issued new metro card for user: {}", userId);
        return metroCardRepository.save(metroCard);
    }

    public MetroCard rechargeMetroCard(String cardNumber, double amount) throws Exception {
        MetroCard metroCard = metroCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new Exception("MetroCard not found"));

        metroCard.setBalance(metroCard.getBalance() + amount);
        log.info("Recharged MetroCard: {}, New Balance: {}", cardNumber, metroCard.getBalance());
        return metroCardRepository.save(metroCard);
    }
}
