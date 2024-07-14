package io.hhplus.server.infrastructure.reservation.entity;

import io.hhplus.server.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long concertScheduleId;

    @ElementCollection
    private List<Long> concertSeatIds;

    private Reservation.Status status;
    private LocalDateTime createdAt;

    public static ReservationEntity from(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.id = reservation.getId();
        entity.userId = reservation.getUserId();
        entity.concertScheduleId = reservation.getConcertScheduleId();
        entity.concertSeatIds = reservation.getConcertSeatIds();
        entity.status = reservation.getStatus();
        entity.createdAt = reservation.getCreatedAt();

        return entity;
    }

    public static Reservation toDomain(ReservationEntity entity) {
        return Reservation.builder()
                          .id(entity.id)
                          .userId(entity.userId)
                          .concertScheduleId(entity.concertScheduleId)
                          .concertSeatIds(entity.concertSeatIds)
                          .status(entity.status)
                          .createdAt(entity.createdAt)
                          .build();
    }
}
