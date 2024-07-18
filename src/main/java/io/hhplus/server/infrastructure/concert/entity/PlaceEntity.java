package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.Place;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place")
@NoArgsConstructor
public class PlaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public static Place toDomain(PlaceEntity entity) {
        return Place.builder()
                    .id(entity.id)
                    .name(entity.name)
                    .build();
    }
}
