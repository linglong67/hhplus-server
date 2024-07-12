package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.infrastructure.concert.entity.ConcertSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
}