package io.hhplus.server.domain.payment.message;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PaymentOutbox {
    private Long id;
    private Long paymentId;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum Status {
        INIT,
        PUBLISHED
    }
}
