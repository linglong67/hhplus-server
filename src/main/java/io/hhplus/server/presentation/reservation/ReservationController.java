package io.hhplus.server.presentation.reservation;

import io.hhplus.server.application.reservation.ReservationFacade;
import io.hhplus.server.presentation.reservation.dto.ReservationRequest;
import io.hhplus.server.presentation.reservation.dto.ReservationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "예약",description = "콘서트 좌석 예약을 위한 API")
public class ReservationController {
    private final ReservationFacade reservationFacade;

    @PostMapping
    @Operation(summary = "예약", description = "콘서트 좌석을 예약하는 API")
    public ReservationResponse reserveSeat(
            @RequestHeader("Queue-Token") String token,
            @RequestBody ReservationRequest request) {
        return ReservationResponse.toResponse(reservationFacade.reserveSeat(token, ReservationRequest.toDto(request)));
    }
}
