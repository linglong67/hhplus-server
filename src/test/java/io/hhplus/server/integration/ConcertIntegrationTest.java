package io.hhplus.server.integration;

import io.hhplus.server.application.concert.ConcertDto;
import io.hhplus.server.application.concert.ConcertFacade;
import io.hhplus.server.application.concert.ConcertScheduleDto;
import io.hhplus.server.application.concert.ConcertSeatDto;
import io.hhplus.server.domain.concert.ConcertRepository;
import io.hhplus.server.domain.queue.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ConcertIntegrationTest {
    @Autowired
    private ConcertFacade concertFacade;

    @Autowired
    private QueueService queueService;

    @Autowired
    private ConcertRepository concertRepository;

    @Test
    @DisplayName("콘서트 목록 조회")
    void getConcerts_success() {
        //given
        //when
        List<ConcertDto> concerts = concertFacade.getConcerts();

        //then
        assertThat(concerts).hasSize(3);
    }

    @Test
    @DisplayName("콘서트 예약가능 날짜 조회")
    void getAvailableDates_success() {
        //given
        long concertId = 1L;

        //when
        List<ConcertScheduleDto> availableDates = concertFacade.getAvailableDates(concertId);

        //then
        assertThat(availableDates).hasSize(2);
    }

    @Test
    @DisplayName("콘서트 예약가능 좌석 조회")
    void getAvailableSeats_success() {
        //given
        long concertId = 1L;
        long concertScheduleId = 1L;

        //when
        List<ConcertSeatDto> availableSeats = concertFacade.getAvailableSeats(concertId, concertScheduleId);

        //then
        assertThat(availableSeats).size().isLessThan(50);
    }
}