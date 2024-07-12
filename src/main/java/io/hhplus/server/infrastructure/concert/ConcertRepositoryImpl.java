package io.hhplus.server.infrastructure.concert;

import io.hhplus.server.domain.concert.Concert;
import io.hhplus.server.domain.concert.ConcertRepository;
import io.hhplus.server.domain.concert.ConcertSchedule;
import io.hhplus.server.domain.concert.ConcertSeat;
import io.hhplus.server.infrastructure.concert.repository.ConcertJpaRepository;
import io.hhplus.server.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import io.hhplus.server.infrastructure.concert.repository.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertRepository;
    private final ConcertScheduleJpaRepository concertScheduleRepository;
    private final ConcertSeatJpaRepository concertSeatRepository;

    @Override
    public List<Concert> findAll() {
        return List.of();
    }

    @Override
    public List<ConcertSchedule> getAvailableDates(long concertId) {
        return List.of();
    }

    @Override
    public List<ConcertSeat> getAvailableSeats(long concertId, long concertScheduleId) {
        return List.of();
    }
}