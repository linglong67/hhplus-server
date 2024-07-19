package io.hhplus.server.infrastructure.reservation.entity;

import io.hhplus.server.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long concertScheduleId;

    private Long placeId;

    private String concertTitle;
    private String concertCasting;
    private LocalDateTime concertDatetime;
    private String placeName;
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private Reservation.Status status;

    private String createdBy;
    private String updatedBy;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static ReservationEntity from(Reservation reservation) {
        ReservationEntity entity = new ReservationEntity();
        entity.id = reservation.getId();
        entity.userId = reservation.getUserId();
        entity.concertScheduleId = reservation.getConcertScheduleId();
        entity.placeId = reservation.getPlaceId();
        entity.concertTitle = reservation.getConcertTitle();
        entity.concertCasting = reservation.getConcertCasting();
        entity.concertDatetime = reservation.getConcertDatetime();
        entity.placeName = reservation.getPlaceName();
        entity.totalPrice = reservation.getTotalPrice();
        entity.status = reservation.getStatus();
        entity.createdBy = reservation.getCreatedBy();
        entity.updatedBy = reservation.getUpdatedBy();
        entity.createdAt = reservation.getCreatedAt();
        entity.updatedAt = reservation.getUpdatedAt();

        return entity;
    }

    public static Reservation toDomain(ReservationEntity entity) {
        return Reservation.builder()
                          .id(entity.id)
                          .userId(entity.userId)
                          .concertScheduleId(entity.concertScheduleId)
                          .placeId(entity.placeId)
                          .concertTitle(entity.concertTitle)
                          .concertCasting(entity.concertCasting)
                          .concertDatetime(entity.concertDatetime)
                          .placeName(entity.placeName)
                          .totalPrice(entity.totalPrice)
                          .status(entity.status)
                          .createdAt(entity.createdAt)
                          .updatedAt(entity.updatedAt)
                          .createdBy(entity.createdBy)
                          .updatedBy(entity.updatedBy)
                          .build();
    }
}
