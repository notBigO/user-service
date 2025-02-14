package com.userservice.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
    private Long id;
    private Long sourceStationId;
    private Long destinationStationId;
    private Double distance;
    private boolean active;
}