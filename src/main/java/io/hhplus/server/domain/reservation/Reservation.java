package io.hhplus.server.domain.reservation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Reservation {
    private Long id;
    private Long userId;
    private Long concertScheduleId;
    private List<Long> concertSeatIds;
    private Long placeId;
    private String concertTitle;
    private String concertCasting;
    private LocalDateTime concertDatetime;
    private String placeName;
    private Integer totalPrice;
    private Status status;
    private String createdBy;
    private LocalDateTime createdAt;
    private String updatedBy;
    private LocalDateTime updatedAt;

    public enum Status {
        RESERVED,
        PAID,
        CANCELED
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
