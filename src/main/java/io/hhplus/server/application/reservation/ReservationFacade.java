package io.hhplus.server.application.reservation;

import io.hhplus.server.domain.concert.ConcertInfo;
import io.hhplus.server.domain.concert.ConcertSeatInfo;
import io.hhplus.server.domain.concert.ConcertService;
import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationFacade {
    private final ReservationService reservationService;
    private final ConcertService concertService;

    // 좌석 예약
    @Transactional
    public ReservationDto reserveSeat(ReservationDto dto) {
        concertService.assignSeats(dto.getConcertSeatIds());

        ConcertInfo concertInfo = concertService.getConcertInfo(dto.getConcertScheduleId());
        List<ConcertSeatInfo> concertSeatInfo = concertService.getConcertSeatInfo(dto.getConcertSeatIds());

        return ReservationDto.toDto(reservationService.reserve(
                dto.toDomain(dto.getUserId(), concertInfo, concertSeatInfo)));
    }

    // 좌석 임시 배정 만료
    @Transactional
    public void releaseSeatHolds() {
        List<Reservation> reservations = reservationService.findUnpaidUsersWithin10MinutesOfReservation();
        reservationService.cancelReservation(reservations);

        List<Long> releaseTargets = reservationService.getConcertSeatIds(reservations.stream().map(Reservation::getId).toList());
        concertService.releaseSeatHolds(releaseTargets);
    }
}
