package io.hhplus.server.interfaces.scheduler;

import io.hhplus.server.application.reservation.ReservationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {
    private final ReservationFacade reservationFacade;

    @Scheduled(fixedDelay = 5000)
    public void releaseSeatHolds() {
        reservationFacade.releaseSeatHolds();
    }
}
