package com.userservice.user.configs;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.user.dtos.FareProcessingEvent;
import com.userservice.user.services.MetroCardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final MetroCardService metroCardService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "fare-processing")
    public void consumeFareProcessingEvent(String message) {
        try {
            FareProcessingEvent event = objectMapper.readValue(message, FareProcessingEvent.class);
            log.info("Fare Processing Event: " + event);
            metroCardService.processFareEvent(event);
        } catch (Exception e) {
            log.error("Error processing fare event: {}", e.getMessage(), e);
        }
    }
}