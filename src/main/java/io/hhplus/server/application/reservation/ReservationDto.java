package io.hhplus.server.application.reservation;

import io.hhplus.server.domain.concert.ConcertInfo;
import io.hhplus.server.domain.concert.ConcertSeatInfo;
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

    public Reservation toDomain(long userId, ConcertInfo concertInfo, List<ConcertSeatInfo> concertSeatInfo) {
        return Reservation.builder()
                          .userId(userId)
                          .concertTitle(concertInfo.getConcertTitle())
                          .concertCasting(concertInfo.getConcertCasting())
                          .concertScheduleId(concertInfo.getConcertScheduleId())
                          .concertDatetime(concertInfo.getConcertDatetime())
                          .placeId(concertInfo.getPlaceId())
                          .placeName(concertInfo.getPlaceName())
                          .createdBy(String.valueOf(userId))
                          .updatedBy(String.valueOf(userId))
                          .tickets(concertSeatInfo.stream()
                                                  .map(ticketInfo -> Reservation.Ticket
                                                          .builder()
                                                          .concertSeatId(ticketInfo.getConcertSeatId())
                                                          .price(ticketInfo.getPrice())
                                                          .seatId(ticketInfo.getSeatId())
                                                          .seatNo(ticketInfo.getSeatNo())
                                                          .build())
                                                  .toList())
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
