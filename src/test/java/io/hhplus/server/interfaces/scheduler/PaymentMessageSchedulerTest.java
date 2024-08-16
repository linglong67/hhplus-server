package io.hhplus.server.interfaces.scheduler;

import io.hhplus.server.domain.payment.message.PaymentMessageProducer;
import io.hhplus.server.domain.payment.message.PaymentOutbox;
import io.hhplus.server.domain.payment.message.PaymentOutboxService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentMessageSchedulerTest {

    @Mock
    private PaymentOutboxService paymentOutboxService;

    @Mock
    private PaymentMessageProducer paymentMessageProducer;

    @InjectMocks
    private PaymentMessageScheduler paymentMessageScheduler;

    @Test
    @DisplayName("스케줄러로 결제 성공 메시지를 재발행")
    void resend_payment_success_message() {
        //given
        PaymentOutbox paymentOutbox = PaymentOutbox.builder()
                                                   .paymentId(10000L)
                                                   .createdAt(LocalDateTime.now().minusMinutes(5))
                                                   .updatedAt(LocalDateTime.now().minusMinutes(5))
                                                   .build();
        when(paymentOutboxService.getMessagesForRetry()).thenReturn(List.of(paymentOutbox));

        //when
        paymentMessageScheduler.resendSuccessMessage();

        //then
        verify(paymentOutboxService, times(1)).getMessagesForRetry();
        verify(paymentMessageProducer, times(1)).sendSuccessMessage(paymentOutbox.getPaymentId());
    }
}