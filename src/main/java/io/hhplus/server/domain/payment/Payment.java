package io.hhplus.server.domain.payment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Payment {
    private Long id;
    private Long reservationId;
    private Long userId;
    private Status status;
    private Integer paidPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        PAID,
        CANCELED
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
