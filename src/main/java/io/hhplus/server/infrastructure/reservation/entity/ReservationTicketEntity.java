package io.hhplus.server.infrastructure.reservation.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation_ticket")
@NoArgsConstructor
public class ReservationTicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
