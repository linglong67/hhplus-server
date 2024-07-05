package io.hhplus.server.presentation.concert.controller;

import io.hhplus.server.presentation.concert.dto.ConcertOptionResponse;
import io.hhplus.server.presentation.concert.dto.ConcertSeatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/concert")
public class ConcertController {

    @GetMapping("{id}")
    public List<ConcertOptionResponse> availableOptions(@PathVariable("id") long concertId) {
        return List.of(
                new ConcertOptionResponse(1L, LocalDate.of(2024, 7, 30), LocalTime.NOON),
                new ConcertOptionResponse(2L, LocalDate.of(2024, 8, 10), LocalTime.MIDNIGHT)
        );
    }

    @GetMapping("{id}/option/{option_id}")
    public List<ConcertSeatResponse> availableSeats(
            @PathVariable("id") long concertId,
            @PathVariable("option_id") long concertOptionId) {
        return List.of(
                new ConcertSeatResponse(1L, 1L, 50_000L),
                new ConcertSeatResponse(11L, 11L, 100_000L)
        );
    }
}
