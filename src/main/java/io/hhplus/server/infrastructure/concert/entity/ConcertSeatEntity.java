package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.ConcertSeat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concert_seat")
@NoArgsConstructor
public class ConcertSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertScheduleId;

    private Long seatId;

    @Enumerated(EnumType.STRING)
    private ConcertSeat.Status status;

    private Integer price;

    @Version
    private Integer version;

    public static ConcertSeatEntity from(ConcertSeat seat) {
        ConcertSeatEntity entity = new ConcertSeatEntity();
        entity.id = seat.getId();
        entity.concertScheduleId = seat.getConcertScheduleId();
        entity.seatId = seat.getSeatId();
        entity.status = seat.getStatus();
        entity.price = seat.getPrice();
        entity.version = seat.getVersion();

        return entity;
    }

    public static ConcertSeat toDomain(ConcertSeatEntity entity) {
        return ConcertSeat.builder()
                          .id(entity.id)
                          .concertScheduleId(entity.concertScheduleId)
                          .seatId(entity.seatId)
                          .status(entity.status)
                          .price(entity.price)
                          .version(entity.version)
                          .build();
    }
}
