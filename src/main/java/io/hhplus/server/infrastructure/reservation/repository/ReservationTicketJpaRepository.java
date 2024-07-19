package io.hhplus.server.infrastructure.reservation.repository;

import io.hhplus.server.infrastructure.reservation.entity.ReservationTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationTicketJpaRepository extends JpaRepository<ReservationTicketEntity, Long> {
    List<Long> findAllByReservationIdIn(List<Long> reservationIds);
}
