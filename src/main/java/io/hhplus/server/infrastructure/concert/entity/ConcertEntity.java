package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.Concert;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert")
@NoArgsConstructor
public class ConcertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String casting;

    public static ConcertEntity from(Concert concert) {
        ConcertEntity entity = new ConcertEntity();
        entity.id = concert.getId();
        entity.title = concert.getTitle();
        entity.casting = concert.getCasting();

        return entity;
    }

    public static Concert toDomain(ConcertEntity entity) {
        return Concert.builder()
                      .id(entity.id)
                      .title(entity.title)
                      .casting(entity.casting)
                      .build();
    }
}