package io.hhplus.server.domain.common;

import io.hhplus.server.domain.payment.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
public class DataPlatformEventListener {
    private final DataPlatformClient dataPlatformClient;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        try {
            dataPlatformClient.sendReservationResult(event.getPayment(), event.getReservation());
        } catch (Exception e) {
            log.error("데이터 플랫폼에 예약 결과 전송 실패");
        }
    }
}
