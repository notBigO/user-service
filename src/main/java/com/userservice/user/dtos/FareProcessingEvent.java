package com.userservice.user.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FareProcessingEvent {
    private String userId;
    private String ticketId;
    private Double fare;
    private Long sourceStationId;
    private Long destinationStationId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}