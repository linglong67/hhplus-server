package io.hhplus.server.domain.payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Payment {
    private Long reservationId;
    private Long userId;
    private Status status;
    private int paidPrice;

    public enum Status {
        PAID,
        CANCELED
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
