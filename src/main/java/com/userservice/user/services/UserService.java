package com.userservice.user.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.userservice.user.dtos.MetroCardDTO;
import com.userservice.user.dtos.TravelHistoryDTO;
import com.userservice.user.dtos.UserDTO;
import com.userservice.user.dtos.UserProfileDTO;
import com.userservice.user.entities.MetroCard;
import com.userservice.user.entities.TravelHistory;
import com.userservice.user.entities.User;
import com.userservice.user.exceptions.UserNotFoundException;
import com.userservice.user.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
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

    private TravelHistoryDTO convertToDTO(TravelHistory history) {
        TravelHistoryDTO dto = new TravelHistoryDTO();
        dto.setId(history.getId());
        dto.setMetroCardNumber(history.getMetroCard().getCardNumber());
        dto.setSourceStationId(history.getSourceStationId());
        dto.setDestinationStationId(history.getDestinationStationId());
        dto.setFare(history.getFare());
        dto.setCheckInTime(history.getCheckInTime());
        dto.setCheckOutTime(history.getCheckOutTime());
        return dto;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    public UserProfileDTO getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<MetroCardDTO> cards = user.getMetroCards().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        List<TravelHistoryDTO> history = user.getTravelHistory().stream()
                .sorted(Comparator.comparing(TravelHistory::getCheckOutTime).reversed())
                .limit(10)
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new UserProfileDTO(
                convertToDTO(user),
                cards,
                history);
    }

}
