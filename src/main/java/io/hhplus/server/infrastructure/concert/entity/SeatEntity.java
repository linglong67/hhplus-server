package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.Place;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@NoArgsConstructor
public class SeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long placeId;

    private Integer seatNo;

    public static Place.Seat toDomain(SeatEntity entity) {
        return Place.Seat.builder()
                         .id(entity.id)
                         .seatNo(entity.seatNo)
                         .build();
    }
}
