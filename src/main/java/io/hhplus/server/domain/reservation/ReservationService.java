package io.hhplus.server.domain.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation reserve(String token, Reservation reservation) {
        return reservationRepository.reserve(token, reservation);
    }
}
