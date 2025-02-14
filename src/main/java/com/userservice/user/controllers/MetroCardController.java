package com.userservice.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.user.dtos.BuyMetroCardDTO;
import com.userservice.user.dtos.MetroCardDTO;
import com.userservice.user.services.MetroCardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users/{userId}/cards")
@RequiredArgsConstructor
public class MetroCardController {
    private final MetroCardService metroCardService;

    @PostMapping
    public ResponseEntity<MetroCardDTO> buyMetroCard(
            @PathVariable Long userId,
            @Valid @RequestBody BuyMetroCardDTO dto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(metroCardService.buyMetroCard(userId, dto));
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<Void> cancelMetroCard(
            @PathVariable Long userId,
            @PathVariable Long cardId) throws Exception {
        metroCardService.cancelMetroCard(userId, cardId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteMetroCard(
            @PathVariable Long userId,
            @PathVariable Long cardId) throws Exception {
        metroCardService.deleteMetroCard(userId, cardId);
        return ResponseEntity.noContent().build();
    }
}