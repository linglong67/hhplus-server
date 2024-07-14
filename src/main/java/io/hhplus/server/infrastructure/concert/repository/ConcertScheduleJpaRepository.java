package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.infrastructure.concert.entity.ConcertScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertScheduleEntity, Long> {
}
