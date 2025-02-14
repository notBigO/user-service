package com.userservice.user.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate createdAt;
}
