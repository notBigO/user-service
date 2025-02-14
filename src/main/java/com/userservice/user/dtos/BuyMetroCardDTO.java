package com.userservice.user.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BuyMetroCardDTO {
    @NotNull
    @Positive
    private Double initialBalance;
}