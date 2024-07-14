package io.hhplus.server.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private static final int MAX_UNPAID_MINUTES = 10;

    public Reservation reserve(Reservation reservation) {
        reservation.updateStatus(Reservation.Status.RESERVED);
        return reservationRepository.reserve(reservation);
    }

    public Reservation updateReservationStatus(long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (reservation.isEmpty()) {
            throw new IllegalStateException("예약 정보를 찾을 수 없음");
        }

        Reservation reservationInfo = reservation.get();
        reservationInfo.updateStatus(Reservation.Status.PAID);

        return reservationRepository.update(reservationInfo);
    }

    public List<Reservation> findUnpaidUsersWithin10MinutesOfReservation() {
        return reservationRepository.findAllByStatusIsAndCreatedAtBefore(Reservation.Status.RESERVED, LocalDateTime.now().minusMinutes(MAX_UNPAID_MINUTES));
    }

    public void cancelReservation(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.updateStatus(Reservation.Status.CANCELED);
            reservationRepository.update(reservation);
        }
    }
}
