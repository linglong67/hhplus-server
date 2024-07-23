package io.hhplus.server.infrastructure.concert;

import io.hhplus.server.domain.concert.*;
import io.hhplus.server.infrastructure.concert.entity.*;
import io.hhplus.server.infrastructure.concert.repository.*;
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
    private final PlaceJpaRepository placeRepository;
    private final SeatJpaRepository seatRepository;

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

    @Override
    public Optional<Concert> findConcert(Long concertId) {
        return concertRepository.findById(concertId).map(ConcertEntity::toDomain);
    }

    @Override
    public Optional<ConcertSchedule> findConcertSchedule(Long concertScheduleId) {
        return concertScheduleRepository.findById(concertScheduleId).map(ConcertScheduleEntity::toDomain);
    }

    @Override
    public Optional<Place> findPlace(Long placeId) {
        return placeRepository.findById(placeId).map(PlaceEntity::toDomain);
    }

    @Override
    public Optional<Place.Seat> findSeat(Long seatId) {
        return seatRepository.findById(seatId).map(SeatEntity::toDomain);
    }
}