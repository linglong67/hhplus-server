package io.hhplus.server.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private static final int MAX_UNPAID_MINUTES = 10;

    public Reservation reserve(String token, Reservation reservation) {
        return reservationRepository.reserve(token, reservation);
    }

    public Reservation updateReservationStatus(long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        reservation.updateStatus(Reservation.Status.PAID);
        return reservationRepository.update(reservation);
    }

    public List<Reservation> findUnpaidUsersWithin10MinutesOfReservation() {
        return reservationRepository.findAllByStatusIsAndCreatedAtBefore(Reservation.Status.RESERVED.name(), LocalDateTime.now().minusMinutes(MAX_UNPAID_MINUTES));
    }

    public void cancelReservation(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.updateStatus(Reservation.Status.CANCELED);
            reservationRepository.update(reservation);
        }
    }
}
