package io.hhplus.server.integration;

import io.hhplus.server.application.concert.ConcertDto;
import io.hhplus.server.application.concert.ConcertFacade;
import io.hhplus.server.application.concert.ConcertScheduleDto;
import io.hhplus.server.application.concert.ConcertSeatDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ConcertIntegrationTest {
    @Autowired
    private ConcertFacade concertFacade;

    @Autowired
    private RedisCacheManager cacheManager;

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

    @Test
    @DisplayName("콘서트 목록 조회 - 캐싱")
    void getConcerts_cache() {
        //given
        cacheManager.getCache("concerts").clear();

        //when
        long nonCachedStartTime = System.currentTimeMillis();
        concertFacade.getConcerts();
        long nonCachedEndTime = System.currentTimeMillis();

        long cachedStartTime = System.currentTimeMillis();
        concertFacade.getConcerts();
        long cachedEndTime = System.currentTimeMillis();

        //then
        assertThat(cachedEndTime - cachedStartTime).isLessThan(nonCachedEndTime - nonCachedStartTime);
    }

    @Test
    @DisplayName("콘서트 예약가능 날짜 조회 - 캐싱")
    void getAvailableDates_cache() {
        //given
        long concertId = 1L;
        cacheManager.getCache("concert-schedules").clear();

        //when
        long nonCachedStartTime = System.currentTimeMillis();
        concertFacade.getAvailableDates(concertId);
        long nonCachedEndTime = System.currentTimeMillis();

        long cachedStartTime = System.currentTimeMillis();
        concertFacade.getAvailableDates(concertId);
        long cachedEndTime = System.currentTimeMillis();

        //then
        assertThat(cachedEndTime - cachedStartTime).isLessThan(nonCachedEndTime - nonCachedStartTime);
    }
}