package io.hhplus.server.domain.reservation;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
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

    private List<Ticket> tickets;

    public enum Status {
        RESERVED,
        PAID,
        CANCELED
    }

    @Getter
    @Builder
    public static class Ticket {
        private Long id;
        private Long reservationId;
        private Long concertSeatId;
        private Long seatId;
        private Integer seatNo;
        private Integer price;

        public void updateReservationId(Long reservationId) {
            this.reservationId = reservationId;
        }
    }

    public void isPaymentProcessable() {
        if (status != Status.RESERVED) {
            throw new BusinessException(ErrorCode.RESERVATION_PAYMENT_UNPROCESSABLE);
        }
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void calculateTotalPrice() {
        this.totalPrice = this.tickets.stream().map(Ticket::getPrice).reduce(0, Integer::sum);
    }

    public void markUpdater(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
