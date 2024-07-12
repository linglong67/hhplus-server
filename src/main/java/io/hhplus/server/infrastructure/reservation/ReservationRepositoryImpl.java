package io.hhplus.server.infrastructure.reservation;

import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationRepository;
import io.hhplus.server.infrastructure.reservation.repository.ReservationJpaRepository;
import io.hhplus.server.infrastructure.reservation.repository.ReservationTicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationRepository;
    private final ReservationTicketJpaRepository reservationTicketRepository;

    @Override
    public Reservation reserve(String token, Reservation request) {
        return null;
    }

    @Override
    public Reservation findById(long reservationId) {
        return null;
    }

    @Override
    public Reservation update(Reservation reservation) {
        return null;
    }
}