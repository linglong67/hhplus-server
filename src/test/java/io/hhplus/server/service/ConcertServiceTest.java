package io.hhplus.server.service;

import io.hhplus.server.domain.common.exception.BusinessException;
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
import java.util.Optional;

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
                               .concertDatetime(LocalDateTime.of(2025, 1, 13, 18, 0)).build(),
                ConcertSchedule.builder().id(2L).concertId(concertId).placeId(1L)
                               .concertDatetime(LocalDateTime.of(2025, 1, 14, 18, 0)).build()
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

        concertSeatIds.forEach(concertSeatId -> {
            Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(concertSeatId);

            if (concertSeat.isPresent()) {
                ConcertSeat seat = concertSeat.get();
                seat.assign();
                concertRepository.update(seat);
            }
        });

        //when
        ArgumentCaptor<Long> concertSeatIdsCaptor = ArgumentCaptor.forClass(Long.class);
        verify(concertRepository, times(concertSeatIds.size())).findConcertSeat(concertSeatIdsCaptor.capture());

        //then
        List<Long> concertSeats = concertSeatIdsCaptor.getAllValues();
        assertThat(concertSeats).isEqualTo(concertSeatIds);
    }

    @Test
    @DisplayName("좌석 할당 실패")
    void assignSeats_failure() {
        //given
        List<Long> concertSeatIds = Arrays.asList(1L, 2L);
        Optional<ConcertSeat> seat = Optional.of(
                ConcertSeat.builder()
                           .id(1L)
                           .concertScheduleId(1L)
                           .seatId(1L)
                           .status(ConcertSeat.Status.OCCUPIED)
                           .price(10000)
                           .build()
        );

        when(concertRepository.findConcertSeat(1L)).thenReturn(seat);
        when(concertRepository.update(seat.get())).thenThrow(ObjectOptimisticLockingFailureException.class);

        //when & then
        BusinessException exception =
                assertThrows(BusinessException.class, () -> concertService.assignSeats(concertSeatIds));

        assertThat(exception.getErrorCode().getMessage()).isEqualTo("이미 선택된 좌석");
    }

    @Test
    @DisplayName("좌석 할당 취소")
    void releaseSeatHolds() {
        //given
        List<Long> releaseTargets = Arrays.asList(1L, 2L);
        ConcertSeat seat1 = Mockito.mock(ConcertSeat.class);
        ConcertSeat seat2 = Mockito.mock(ConcertSeat.class);
        when(concertRepository.findConcertSeat(1L)).thenReturn(Optional.of(seat1));
        when(concertRepository.findConcertSeat(2L)).thenReturn(Optional.of(seat2));

        //when
        concertService.releaseSeatHolds(releaseTargets);

        //then
        verify(seat1).release();
        verify(seat2).release();
        verify(concertRepository).update(seat1);
        verify(concertRepository).update(seat2);
    }
}