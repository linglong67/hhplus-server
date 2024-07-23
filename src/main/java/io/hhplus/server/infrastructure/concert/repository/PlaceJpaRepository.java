package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.infrastructure.concert.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceJpaRepository extends JpaRepository<PlaceEntity, Long> {
}
