package io.hhplus.server.presentation.reservation.dto;

import io.hhplus.server.application.reservation.ReservationDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ReservationRequest {
    private long userId;
    private long concertScheduleId;
    private List<Long> concertSeatIds;

    public static ReservationDto toDto(ReservationRequest request) {
        return ReservationDto.builder()
                             .userId(request.getUserId())
                             .concertScheduleId(request.getConcertScheduleId())
                             .concertSeatIds(request.getConcertSeatIds())
                             .build();
    }
}
