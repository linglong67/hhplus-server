package io.hhplus.server.infrastructure.concert.repository;

import io.hhplus.server.domain.concert.ConcertSeat;
import io.hhplus.server.infrastructure.concert.entity.ConcertSeatEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeatEntity, Long> {
    List<ConcertSeatEntity> findAllByConcertScheduleIdAndStatusIs(long concertScheduleId, ConcertSeat.Status status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT cs FROM ConcertSeatEntity cs WHERE cs.id = :concertSeatId")
    Optional<ConcertSeatEntity> findByIdWithPessimisticLock(long concertSeatId);
}