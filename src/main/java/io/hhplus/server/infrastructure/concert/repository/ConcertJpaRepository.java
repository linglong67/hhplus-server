package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.infrastructure.concert.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<ConcertEntity, Long> {
}