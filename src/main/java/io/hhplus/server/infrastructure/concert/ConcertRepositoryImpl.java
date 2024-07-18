package io.hhplus.server.infrastructure.concert;

import io.hhplus.server.domain.concert.Concert;
import io.hhplus.server.domain.concert.ConcertRepository;
import io.hhplus.server.domain.concert.ConcertSchedule;
import io.hhplus.server.domain.concert.ConcertSeat;
import io.hhplus.server.infrastructure.concert.entity.ConcertEntity;
import io.hhplus.server.infrastructure.concert.entity.ConcertScheduleEntity;
import io.hhplus.server.infrastructure.concert.entity.ConcertSeatEntity;
import io.hhplus.server.infrastructure.concert.repository.ConcertJpaRepository;
import io.hhplus.server.infrastructure.concert.repository.ConcertScheduleJpaRepository;
import io.hhplus.server.infrastructure.concert.repository.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepository {
    private final ConcertJpaRepository concertRepository;
    private final ConcertScheduleJpaRepository concertScheduleRepository;
    private final ConcertSeatJpaRepository concertSeatRepository;

    @Override
    public List<Concert> findAll() {
        return concertRepository.findAll()
                                .stream()
                                .map(ConcertEntity::toDomain)
                                .toList();
    }

    @Override
    public List<ConcertSchedule> getAvailableDates(long concertId) {
        return concertScheduleRepository.findAllByConcertIdAndConcertDatetimeIsAfter(concertId, LocalDateTime.now().minusDays(1))
                                        .stream()
                                        .map(ConcertScheduleEntity::toDomain)
                                        .toList();
    }

    @Override
    public List<ConcertSeat> getAvailableSeats(long concertId, long concertScheduleId) {
        return concertSeatRepository.findAllByConcertScheduleIdAndStatusIs(concertScheduleId, ConcertSeat.Status.AVAILABLE)
                                    .stream()
                                    .map(ConcertSeatEntity::toDomain)
                                    .toList();
    }

    @Override
    public ConcertSeat update(ConcertSeat concertSeat) {
        ConcertSeatEntity entity = ConcertSeatEntity.from(concertSeat);
        return ConcertSeatEntity.toDomain(concertSeatRepository.save(entity));
    }

    @Override
    public Optional<ConcertSeat> findConcertSeat(Long concertSeatId) {
        return concertSeatRepository.findById(concertSeatId).map(ConcertSeatEntity::toDomain);
    }
}