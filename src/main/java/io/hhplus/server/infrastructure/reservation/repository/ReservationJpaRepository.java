package io.hhplus.server.infrastructure.reservation.repository;

import io.hhplus.server.domain.reservation.Reservation;
import io.hhplus.server.infrastructure.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findAllByStatusIsAndCreatedAtBefore(Reservation.Status status, LocalDateTime validationTime);
}
