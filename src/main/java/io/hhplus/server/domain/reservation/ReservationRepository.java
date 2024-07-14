package io.hhplus.server.domain.reservation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    Reservation reserve(Reservation request);

    Optional<Reservation> findById(long reservationId);

    Reservation update(Reservation reservation);

    List<Reservation> findAllByStatusIsAndCreatedAtBefore(Reservation.Status status, LocalDateTime validationTime);
}
