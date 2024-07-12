package io.hhplus.server.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    Reservation reserve(String token, Reservation request);

    Reservation findById(long reservationId);

    Reservation update(Reservation reservation);

    List<Reservation> findAllByStatusIsAndCreatedAtBefore(String status, LocalDateTime validationTime);
}
