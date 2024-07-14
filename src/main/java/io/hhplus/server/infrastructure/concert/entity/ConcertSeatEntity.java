package io.hhplus.server.infrastructure.concert.entity;

import io.hhplus.server.domain.concert.ConcertSeat;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concertSeat")
@NoArgsConstructor
public class ConcertSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long concertScheduleId;
    private Long seatId;
    private Long seatNo;
    private ConcertSeat.Status status;
    private Long price;

    @Version
    private Integer version;    // 낙관적 락으로 동시성 제어

    public static ConcertSeatEntity from(ConcertSeat seat) {
        ConcertSeatEntity entity = new ConcertSeatEntity();
        entity.id = seat.getId();
        entity.concertScheduleId = seat.getConcertScheduleId();
        entity.seatId = seat.getSeatId();
        entity.seatNo = seat.getSeatNo();
        entity.status = seat.getStatus();
        entity.price = seat.getPrice();

        return entity;
    }

    public static ConcertSeat toDomain(ConcertSeatEntity entity) {
        return ConcertSeat.builder()
                          .id(entity.id)
                          .concertScheduleId(entity.concertScheduleId)
                          .seatId(entity.seatId)
                          .seatNo(entity.seatNo)
                          .status(entity.status)
                          .price(entity.price)
                          .build();
    }
}
