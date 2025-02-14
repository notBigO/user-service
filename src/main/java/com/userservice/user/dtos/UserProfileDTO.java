package com.userservice.user.dtos;

import java.util.List;

import lombok.Data;

@Data
public class UserProfileDTO {
    private UserDTO user;
    private List<MetroCardDTO> metroCards;
    private List<TravelHistoryDTO> travelHistory;
}
