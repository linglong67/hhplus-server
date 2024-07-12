package io.hhplus.server.domain.reservation;

public interface ReservationRepository {
    Reservation reserve(String token, Reservation request);

    Reservation findById(long reservationId);

    Reservation update(Reservation reservation);
}
