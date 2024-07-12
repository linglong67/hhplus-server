package io.hhplus.server.integration;

import io.hhplus.server.application.reservation.ReservationDto;
import io.hhplus.server.application.reservation.ReservationFacade;
import io.hhplus.server.domain.concert.ConcertService;
import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationRepository;
import io.hhplus.server.domain.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ReservationIntegrationTest {
    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ReservationRepository reservationRepository;

    private Queue queue;
    private ReservationDto reservationDto;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        queue = Queue.builder()
                     .userId(1L)
                     .token("testToken")
                     .status(Queue.Status.ACTIVE)
                     .build();
        queue = queueService.generateToken(queue.getUserId());
        queueService.activateTokens(List.of(queue));

        reservationDto = ReservationDto.builder()
                                       .userId(1L)
                                       .concertSeatIds(List.of(1L, 2L))
                                       .build();

        reservation = Reservation.builder()
                                 .userId(1L)
                                 .concertSeatIds(List.of(1L, 2L))
                                 .status(Reservation.Status.RESERVED)
                                 .createdAt(LocalDateTime.now().minusMinutes(11))
                                 .build();
    }

    @Test
    void reserveSeat() {
        //given
        String token = queue.getToken();

        //when
        ReservationDto reserved = reservationFacade.reserveSeat(token, reservationDto);

        //then
        assertNotNull(reserved);
        assertEquals(queue.getUserId(), reserved.getUserId());
        assertEquals(Reservation.Status.RESERVED, reservationRepository.findById(reserved.getReservationId()).orElseThrow().getStatus());
    }
}