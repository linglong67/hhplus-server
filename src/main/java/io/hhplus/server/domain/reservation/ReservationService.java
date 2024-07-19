package io.hhplus.server.domain.reservation;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
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
    private static final String UPDATED_BY_SYSTEM = "system";

    public Reservation reserve(Reservation reservation) {
        reservation.updateStatus(Reservation.Status.RESERVED);
        reservation.calculateTotalPrice();
        Reservation reservationInfo = reservationRepository.reserve(reservation);

        reservation.getTickets().forEach(ticket -> ticket.updateReservationId(reservationInfo.getId()));
        reservationRepository.issueTickets(reservation.getTickets());

        return reservationInfo;
    }

    public Reservation updateReservationStatus(long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);

        if (reservation.isEmpty()) {
            throw new BusinessException(ErrorCode.RESERVATION_NOT_FOUND);
        }

        Reservation reservationInfo = reservation.get();
        reservationInfo.isPaymentProcessable();
        reservationInfo.updateStatus(Reservation.Status.PAID);

        return reservationRepository.update(reservationInfo);
    }

    public List<Reservation> findUnpaidUsersWithin10MinutesOfReservation() {
        return reservationRepository.findAllByStatusIsAndCreatedAtBefore(Reservation.Status.RESERVED, LocalDateTime.now().minusMinutes(MAX_UNPAID_MINUTES));
    }

    public void cancelReservation(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            reservation.updateStatus(Reservation.Status.CANCELED);
            reservation.markUpdater(UPDATED_BY_SYSTEM);
            reservationRepository.update(reservation);
        }
    }

    public List<Long> getConcertSeatIds(List<Long> reservationIds) {
        return reservationRepository.getConcertSeatIds(reservationIds);
    }
}
