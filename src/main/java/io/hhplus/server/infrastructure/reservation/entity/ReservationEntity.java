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

    // TODO:: ReservationTicket 과의 관계를 어떻게 풀어낼까? (1:N - 연관 관계를 안 맺는다?)
    // 아니면 여기서는 연관 관계를 단방향으로 맺고 도메인에서는 관여하지 않는다?
    @ElementCollection
    private List<Long> concertSeatIds;

    private Long userId;

    private Long concertScheduleId;

    // TODO: 연결 방법 고민하기 (만약 번거롭다면 사라질 수도..)
    private Long placeId;

    private String concertTitle;
    private String concertCasting;
    private LocalDateTime concertDatetime;
    private String placeName;
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private Reservation.Status status;

    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public static ReservationEntity from(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.id = reservation.getId();
        entity.userId = reservation.getUserId();
        entity.concertScheduleId = reservation.getConcertScheduleId();
        entity.status = reservation.getStatus();
        entity.createdAt = reservation.getCreatedAt();
        entity.concertSeatIds = reservation.getConcertSeatIds();    //FIXME:

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
