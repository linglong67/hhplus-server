package io.hhplus.server.presentation.reservation.controller;

import io.hhplus.server.presentation.reservation.dto.ReservationRequest;
import io.hhplus.server.presentation.reservation.dto.ReservationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @PostMapping
    public ReservationResponse reserveSeat(@RequestBody ReservationRequest request) {
        return new ReservationResponse(1L, LocalDateTime.now(), "PENDING_PAYMENT", 150_000L);
    }
}
