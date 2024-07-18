package io.hhplus.server.application.reservation;

import io.hhplus.server.domain.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationDto {
    private long reservationId;
    private long userId;
    private long concertScheduleId;
    private List<Long> concertSeatIds;

    public Reservation toDomain() {
        return Reservation.builder()
                          .userId(userId)
                          .concertScheduleId(concertScheduleId)
                          .concertSeatIds(concertSeatIds)
                          .build();
    }

    public static ReservationDto toDto(Reservation reservation) {
        return ReservationDto.builder()
                             .reservationId(reservation.getId())
                             .userId(reservation.getUserId())
                             .concertScheduleId(reservation.getConcertScheduleId())
                             .build();
    }
}
