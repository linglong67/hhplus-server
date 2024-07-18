package io.hhplus.server.application.concert;

import io.hhplus.server.domain.concert.ConcertService;
import io.hhplus.server.domain.queue.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConcertFacade {
    private final ConcertService concertService;
    private final QueueService queueService;

    // 콘서트 목록 조회
    @Transactional(readOnly = true)
    public List<ConcertDto> getConcerts() {
        return concertService.getConcerts()
                             .stream()
                             .map(ConcertDto::toDto)
                             .toList();
    }

    // 콘서트 예약가능 날짜 조회 (D-1 기준)
    @Transactional(readOnly = true)
    public List<ConcertScheduleDto> getAvailableDates(long concertId) {
        return concertService.getAvailableDates(concertId)
                             .stream()
                             .map(ConcertScheduleDto::toDto)
                             .toList();
    }

    // 콘서트 예약가능 좌석 조회
    @Transactional(readOnly = true)
    public List<ConcertSeatDto> getAvailableSeats(long concertId, long concertScheduleId) {
        return concertService.getAvailableSeats(concertId, concertScheduleId)
                             .stream()
                             .map(ConcertSeatDto::toDto)
                             .toList();
    }
}
