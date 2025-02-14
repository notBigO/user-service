package com.userservice.user.services;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.userservice.user.dtos.CheckInDTO;
import com.userservice.user.dtos.CheckInResponse;
import com.userservice.user.dtos.RouteDTO;
import com.userservice.user.dtos.StationDTO;
import com.userservice.user.exceptions.InvalidOperationException;
import com.userservice.user.exceptions.MetroCardNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MetroServiceClient {
        private final WebClient metroServiceWebClient;

        public Mono<StationDTO> getStation(Long stationId) {
                return metroServiceWebClient.get()
                                .uri("/api/v1/stations/{stationId}", stationId)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError(),
                                                response -> Mono
                                                                .error(new MetroCardNotFoundException(
                                                                                "Station not found")))
                                .onStatus(status -> status.is5xxServerError(),
                                                response -> Mono.error(new Exception("Metro service unavailable")))
                                .bodyToMono(StationDTO.class)
                                .doOnError(error -> log.error("Error fetching station: {}", error.getMessage()));
        }

        public Mono<List<RouteDTO>> getRoutes() {
                return metroServiceWebClient.get()
                                .uri("/api/v1/routes")
                                .retrieve()
                                .onStatus(status -> status.is5xxServerError(),
                                                response -> Mono.error(new Exception("Metro service unavailable")))
                                .bodyToMono(new ParameterizedTypeReference<List<RouteDTO>>() {
                                })
                                .doOnError(error -> log.error("Error fetching routes: {}", error.getMessage()));
        }

        public Mono<CheckInResponse> checkIn(CheckInDTO request) {
                return metroServiceWebClient.post()
                                .uri("/api/v1/check-in")
                                .bodyValue(request)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError(),
                                                response -> Mono.error(
                                                                new InvalidOperationException(
                                                                                "Invalid check-in request")))
                                .onStatus(status -> status.is5xxServerError(),
                                                response -> Mono.error(new Exception("Metro service unavailable")))
                                .bodyToMono(CheckInResponse.class)
                                .doOnError(error -> log.error("Error during check-in: {}", error.getMessage()));
        }

        public Mono<Void> triggerSOS(Long stationId, String userId) {
                return metroServiceWebClient.post()
                                .uri("/api/v1/sos/{stationId}/{userId}", stationId, userId)
                                .retrieve()
                                .onStatus(status -> status.is4xxClientError(),
                                                response -> Mono
                                                                .error(new Exception("Invalid SOS request")))
                                .onStatus(status -> status.is5xxServerError(),
                                                response -> Mono.error(new Exception("Metro service unavailable")))
                                .bodyToMono(Void.class)
                                .doOnError(error -> log.error("Error triggering SOS: {}", error.getMessage()));
        }
}
