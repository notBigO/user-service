package com.userservice.user.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.userservice.user.dtos.BuyMetroCardDTO;
import com.userservice.user.dtos.FareProcessingEvent;
import com.userservice.user.dtos.MetroCardDTO;
import com.userservice.user.entities.MetroCard;
import com.userservice.user.entities.MetroCardStatus;
import com.userservice.user.entities.TravelHistory;
import com.userservice.user.entities.User;
import com.userservice.user.exceptions.InsufficientBalanceException;
import com.userservice.user.exceptions.InvalidOperationException;
import com.userservice.user.exceptions.MetroCardNotFoundException;
import com.userservice.user.exceptions.UserNotFoundException;
import com.userservice.user.repositories.MetroCardRepository;
import com.userservice.user.repositories.TravelHistoryRepository;
import com.userservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetroCardService {
    private final MetroCardRepository metroCardRepository;
    private final UserRepository userRepository;
    private final TravelHistoryRepository travelHistoryRepository;

    public MetroCardDTO getMetroCard(String cardNumber) {
        MetroCard card = metroCardRepository.findByCardNumber(cardNumber).get();

        return convertToDTO(card);
    }

    public MetroCardDTO buyMetroCard(Long userId, BuyMetroCardDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String cardNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        Optional<MetroCard> cardExisting = metroCardRepository.findByCardNumber(cardNumber);
        if (cardExisting.isPresent()) {
            throw new MetroCardNotFoundException("User already has a metro card.");
        }

        MetroCard card = new MetroCard();
        card.setCardNumber(cardNumber);
        card.setBalance(dto.getInitialBalance());
        card.setExpirationDate(LocalDate.now().plusYears(1));
        card.setUser(user);

        MetroCard savedCard = metroCardRepository.save(card);
        log.info("New metro card purchased: {}", savedCard.getCardNumber());
        return convertToDTO(savedCard);
    }

    public void cancelMetroCard(Long userId, Long cardId) {
        MetroCard card = metroCardRepository.findByIdAndUserId(cardId, userId)
                .orElseThrow(() -> new MetroCardNotFoundException("Card not found"));

        if (travelHistoryRepository.existsByMetroCardId(cardId)) {
            throw new InvalidOperationException("Card has been used and cannot be canceled");
        }

        if (card.getStatus() == MetroCardStatus.CANCELED) {
            throw new InvalidOperationException("Card is already canceled");
        }

        card.setStatus(MetroCardStatus.CANCELED);
        metroCardRepository.save(card);
        log.info("Metro card canceled: {}", card.getCardNumber());
    }

    public void deleteMetroCard(Long userId, Long cardId) {
        MetroCard card = metroCardRepository.findByIdAndUserId(cardId, userId)
                .orElseThrow(() -> new MetroCardNotFoundException("Card not found"));

        if (card.getStatus() != MetroCardStatus.CANCELED) {
            throw new InvalidOperationException("Active card cannot be deleted.");
        }

        metroCardRepository.delete(card);
        log.info("Metro card deleted");
    }

    public void processFareEvent(FareProcessingEvent event) throws Exception {
        MetroCard card = metroCardRepository.findByCardNumber(event.getTicketId())
                .orElseThrow(() -> {
                    log.error("Metro card not found: {}", event.getTicketId());
                    return new MetroCardNotFoundException("Metro card not found");
                });

        double newBalance = card.getBalance() - event.getFare();
        if (newBalance < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        card.setBalance(newBalance);
        metroCardRepository.save(card);

        createTravelHistory(card, event);
    }

    private void createTravelHistory(MetroCard card, FareProcessingEvent event) {
        TravelHistory history = new TravelHistory();
        history.setUser(card.getUser());
        history.setMetroCard(card);
        history.setSourceStationId(event.getSourceStationId());
        history.setDestinationStationId(event.getDestinationStationId());
        history.setFare(event.getFare());
        history.setCheckInTime(event.getCheckInTime());
        history.setCheckOutTime(event.getCheckOutTime());

        travelHistoryRepository.save(history);
    }

    private MetroCardDTO convertToDTO(MetroCard card) {
        MetroCardDTO dto = new MetroCardDTO();
        dto.setId(card.getId());
        dto.setCardNumber(card.getCardNumber());
        dto.setBalance(card.getBalance());
        dto.setExpirationDate(card.getExpirationDate());
        dto.setCreatedAt(card.getCreatedAt());
        dto.setStatus(card.getStatus());
        return dto;
    }
}