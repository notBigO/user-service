package com.userservice.user.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TravelHistoryDTO {
    private Long id;
    private String metroCardNumber;
    private Long sourceStationId;
    private Long destinationStationId;
    private Double fare;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
}