package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.domain.concert.ConcertSeat;
import io.hhplus.server.infrastructure.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
    List<ConcertSeatEntity> findAllByConcertScheduleIdAndStatusIs(long concertScheduleId, ConcertSeat.Status status);
}