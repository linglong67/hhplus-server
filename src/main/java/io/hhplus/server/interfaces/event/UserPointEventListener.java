package io.hhplus.server.interfaces.event;

import io.hhplus.server.domain.payment.event.PaymentSuccessEvent;
import io.hhplus.server.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserPointEventListener {
    private final UserService userService;

    // 포인트 사용 실패에 대한 보상 트랜잭션 구현 전이라 "BEFORE_COMMIT"으로 설정했습니다
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        userService.usePoint(event.getPayment().getUserId(), event.getPayment().getPaidPrice());
    }
}
