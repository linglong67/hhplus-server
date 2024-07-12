package io.hhplus.server.service;

import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationRepository;
import io.hhplus.server.domain.reservation.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    void reserve_success() {
        //given
        String token = "testToken";
        Reservation reservation = Reservation.builder().build();
        when(reservationRepository.reserve(reservation)).thenReturn(reservation);

        //when
        Reservation result = reservationService.reserve(reservation);

        //then
        verify(reservationRepository).reserve(reservation);
        assertThat(result).isEqualTo(reservation);
    }

    @Test
    void updateReservationStatus_success() {
        //given
        long reservationId = 1L;
        Reservation reservation = Reservation.builder().build();
        reservation.updateStatus(Reservation.Status.RESERVED);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.update(any(Reservation.class))).thenReturn(reservation);

        //when
        Reservation result = reservationService.updateReservationStatus(reservationId);

        //then
        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository).update(reservation);
        assertThat(result.getStatus()).isEqualTo(Reservation.Status.PAID);
    }

    @Test
    void findUnpaidUsersWithin10MinutesOfReservation_success() {
        //given
        Reservation reservation1 = Reservation.builder().build();
        Reservation reservation2 = Reservation.builder().build();
        List<Reservation> reservations = List.of(reservation1, reservation2);
        when(reservationRepository.findAllByStatusIsAndCreatedAtBefore(any(Reservation.Status.class), any(LocalDateTime.class))).thenReturn(reservations);

        //when
        List<Reservation> result = reservationService.findUnpaidUsersWithin10MinutesOfReservation();

        //then
        verify(reservationRepository).findAllByStatusIsAndCreatedAtBefore(any(Reservation.Status.class), any(LocalDateTime.class));
        assertThat(result).isEqualTo(reservations);
    }

    @Test
    void cancelReservation_success() {
        //given
        Reservation reservation1 = Reservation.builder().build();
        Reservation reservation2 = Reservation.builder().build();
        List<Reservation> reservations = List.of(reservation1, reservation2);

        //when
        reservationService.cancelReservation(reservations);

        //then
        for (Reservation reservation : reservations) {
            verify(reservationRepository).update(reservation);
            assertThat(reservation.getStatus()).isEqualTo(Reservation.Status.CANCELED);
        }
    }
}