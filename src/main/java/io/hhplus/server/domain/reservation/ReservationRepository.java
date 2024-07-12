package io.hhplus.server.domain.reservation;

public interface ReservationRepository {
    Reservation reserve(String token, Reservation request);
}
