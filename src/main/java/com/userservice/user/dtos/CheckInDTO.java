package com.userservice.user.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckInDTO {
    private String userId;
    private Long stationId;
    private String ticketType;
    private String ticketId;
    private LocalDateTime checkInTime;
}