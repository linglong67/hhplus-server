package io.hhplus.server.service;

import io.hhplus.server.domain.concert.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {
    @InjectMocks
    private ConcertService concertService;

    @Mock
    private ConcertRepository concertRepository;

    @Test
    @DisplayName("콘서트 목록 조회")
    void getConcerts() {
        //given
        List<Concert> expected = List.of(
                Concert.builder().id(1L).title("A").casting("가").build(),
                Concert.builder().id(2L).title("B").casting("나").build(),
                Concert.builder().id(3L).title("C").casting("다").build()
        );

        //when
        when(concertRepository.findAll()).thenReturn(expected);

        //then
        assertThat(concertService.getConcerts()).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 콘서트 스케줄 조회")
    void getAvailableDates() {
        //given
        Long concertId = 1L;
        List<ConcertSchedule> expected = List.of(
                ConcertSchedule.builder().id(1L).concertId(concertId).placeId(1L)
                               .concertDateTime(LocalDateTime.of(2025, 1, 13, 18, 0)).build(),
                ConcertSchedule.builder().id(2L).concertId(concertId).placeId(1L)
                               .concertDateTime(LocalDateTime.of(2025, 1, 14, 18, 0)).build()
        );

        //when
        when(concertRepository.getAvailableDates(concertId)).thenReturn(expected);

        //then
        assertThat(concertService.getAvailableDates(concertId)).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 콘서트 좌석 조회")
    void getAvailableSeats() {
        //given
        List<ConcertSeat> expected = List.of(
                ConcertSeat.builder().id(1L).build(),
                ConcertSeat.builder().id(2L).build()
        );

        //when
        when(concertRepository.getAvailableSeats(1L, 1L)).thenReturn(expected);

        //then
        assertThat(concertService.getAvailableSeats(1L, 1L)).isEqualTo(expected);
    }

    @Test
    @DisplayName("좌석 할당")
    void assignSeats() {
        //given
        List<Long> concertSeatIds = List.of(1L, 2L);

        for (Long concertSeatId : concertSeatIds) {
            concertRepository.assignSeat(concertSeatId);
        }

        //when
        ArgumentCaptor<Long> concertSeatIdCaptor = ArgumentCaptor.forClass(Long.class);
        verify(concertRepository, times(concertSeatIds.size())).assignSeat(concertSeatIdCaptor.capture());

        //then
        List<Long> capturedSeatIds = concertSeatIdCaptor.getAllValues();
        assertThat(capturedSeatIds).isEqualTo(concertSeatIds);
    }

    @Test
    @DisplayName("좌석 할당 실패")
    void assignSeats_failure() {
        //given
        List<Long> concertSeatIds = Arrays.asList(1L, 2L);
        when(concertRepository.assignSeat(1L)).thenThrow(ObjectOptimisticLockingFailureException.class);

        //when & then
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> concertService.assignSeats(concertSeatIds));

        assertThat(exception.getMessage()).isEqualTo("이미 선택된 좌석");
    }

    @Test
    @DisplayName("좌석 할당 취소")
    void releaseSeatHolds() {
        //given
        List<Long> releaseTargets = Arrays.asList(1L, 2L);
        ConcertSeat seat1 = Mockito.mock(ConcertSeat.class);
        ConcertSeat seat2 = Mockito.mock(ConcertSeat.class);
        when(concertRepository.findConcertSeat(1L)).thenReturn(seat1);
        when(concertRepository.findConcertSeat(2L)).thenReturn(seat2);

        //when
        concertService.releaseSeatHolds(releaseTargets);

        //then
        verify(seat1).release();
        verify(seat2).release();
        verify(concertRepository).update(seat1);
        verify(concertRepository).update(seat2);
    }
}