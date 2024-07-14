package io.hhplus.server.presentation.reservation.dto;

import io.hhplus.server.application.reservation.ReservationDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationResponse {
    //TODO: 응답값 더 많이 채워야함
    private long reservationId;
    private long userId;
    private long concertScheduleId;

    public static ReservationResponse toResponse(ReservationDto dto) {
        return ReservationResponse.builder()
                                  .reservationId(dto.getReservationId())
                                  .userId(dto.getUserId())
                                  .concertScheduleId(dto.getConcertScheduleId())
                                  .build();
    }
}