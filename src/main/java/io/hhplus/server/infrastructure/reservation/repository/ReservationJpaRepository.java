package io.hhplus.server.infrastructure.reservation.repository;

import io.hhplus.server.infrastructure.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
}
