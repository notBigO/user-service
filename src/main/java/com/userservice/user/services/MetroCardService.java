package com.userservice.user.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.userservice.user.entities.MetroCard;
import com.userservice.user.repositories.MetroCardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetroCardService {
    private final MetroCardRepository metroCardRepository;

    public MetroCard buyMetroCard(String userId, double balance) {
        MetroCard metroCard = new MetroCard();
        metroCard.setUserId(userId);
        metroCard.setCardNumber(UUID.randomUUID().toString());
        metroCard.setBalance(balance);
        metroCard.setActive(true);

        metroCardRepository.save(metroCard);
        log.info("Issued new metro card for user: {}", userId, metroCard);

        return metroCard;
    }

    public void cancelMetroCard(String userId) throws Exception {
        MetroCard card = metroCardRepository.findByUserId(userId)
                .orElseThrow(() -> new Exception("No active Metro Card found"));

        if (card.getBalance() > 0) {
            throw new Exception("Cannot cancel card with remaining balance");
        }

        card.setActive(false);
        metroCardRepository.save(card);
        log.info("Metro Card canceled for user: {}", userId);
    }
}
