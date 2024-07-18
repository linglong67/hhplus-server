package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.infrastructure.concert.entity.ConcertScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {
    List<ConcertScheduleEntity> findAllByConcertIdAndConcertDatetimeIsAfter(long concertId, LocalDateTime availableDatetime);
}
