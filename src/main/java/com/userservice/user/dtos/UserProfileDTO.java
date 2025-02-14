package com.userservice.user.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private UserDTO user;
    private List<MetroCardDTO> metroCards;
    private List<TravelHistoryDTO> travelHistory;
}
