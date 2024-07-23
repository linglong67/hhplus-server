package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.ConcertSchedule;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert_schedule")
@NoArgsConstructor
public class ConcertScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertId;

    private Long placeId;

    private LocalDateTime concertDatetime;

    public static ConcertScheduleEntity from(ConcertSchedule concertSchedule) {
        ConcertScheduleEntity entity = new ConcertScheduleEntity();
        entity.id = concertSchedule.getId();
        entity.concertId = concertSchedule.getConcertId();
        entity.placeId = concertSchedule.getPlaceId();
        entity.concertDatetime = concertSchedule.getConcertDatetime();

        return entity;
    }

    public static ConcertSchedule toDomain(ConcertScheduleEntity entity) {
        return ConcertSchedule.builder()
                              .id(entity.id)
                              .concertId(entity.concertId)
                              .placeId(entity.placeId)
                              .concertDatetime(entity.concertDatetime)
                              .build();
    }
}
