package io.hhplus.server.integration;

import io.hhplus.server.application.reservation.ReservationDto;
import io.hhplus.server.application.reservation.ReservationFacade;
import io.hhplus.server.domain.concert.ConcertRepository;
import io.hhplus.server.domain.concert.ConcertSeat;
import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.domain.reservation.ReservationRepository;
import io.hhplus.server.domain.reservation.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

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
    private ReservationRepository reservationRepository;

    @Autowired
    private ConcertRepository concertRepository;

    private Queue queue;
    private ReservationDto reservationDto;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        queue = Queue.builder()
                     .userId(1L)
                     .token("testToken")
                     .status(Queue.Status.ACTIVE)
                     .build();
        queue = queueService.generateToken(queue.getUserId());
        queueService.activateTokens(List.of(queue));

        reservationDto = ReservationDto.builder()
                                       .userId(1L)
                                       .concertScheduleId(1L)
                                       .concertSeatIds(List.of(1L, 2L))
                                       .build();

        reservation = Reservation.builder()
                                 .userId(1L)
                                 .status(Reservation.Status.RESERVED)
                                 .tickets(List.of(Reservation.Ticket.builder().concertSeatId(4L).build(),
                                         Reservation.Ticket.builder().concertSeatId(5L).build()))
                                 .createdAt(LocalDateTime.now().minusMinutes(11))
                                 .build();
    }

    @Test
    @DisplayName("좌석 예약")
    void reserveSeat() {
        //given
        //when
        ReservationDto reserved = reservationFacade.reserveSeat(reservationDto);

        //then
        assertNotNull(reserved);
        assertEquals(queue.getUserId(), reserved.getUserId());
        assertEquals(Reservation.Status.RESERVED, reservationRepository.findById(reserved.getReservationId()).orElseThrow().getStatus());
    }

    // FIXME: 실패 케이스
    @Test
    @DisplayName("좌석 임시 배정 해제")
    void releaseSeatHolds() {
        //given
        reservationRepository.reserve(reservation);

        List<Reservation> reservationsBefore = reservationService.findUnpaidUsersWithin10MinutesOfReservation();

        //when
        reservationFacade.releaseSeatHolds();

        //then
        List<Reservation> reservationsAfter = reservationService.findUnpaidUsersWithin10MinutesOfReservation();
        assertTrue(reservationsAfter.isEmpty());

        List<Long> concertSeatIds = reservation.getTickets().stream().map(Reservation.Ticket::getConcertSeatId).toList();
        concertSeatIds.forEach(concertSeatId ->
                assertEquals(ConcertSeat.Status.AVAILABLE, concertRepository.findConcertSeat(concertSeatId).get().getStatus())
        );
    }

    @Test
    @DisplayName("좌석 예약 동시성 테스트 - 비관적 락 (베타 락)")
    void reserveSeat_with_optimistic_lock() throws InterruptedException {
        //given
        final int threadCount = 3000;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        AtomicLong userId = new AtomicLong(1L);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    reservationFacade.reserveSeat(
                            ReservationDto.builder()
                                          .userId(userId.getAndIncrement())
                                          .concertScheduleId(1L)
                                          .concertSeatIds(List.of(20L))
                                          .build());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertEquals(threadCount - failCount.intValue(), successCount.intValue());
    }
}