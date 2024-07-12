package io.hhplus.server.infrastructure.reservation.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long concertScheduleId;
//    private List<Long> concertSeatIds;
    private String concertSeatIds;
    private String status;
    private LocalDateTime createdAt;
}
