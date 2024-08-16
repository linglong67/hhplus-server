package io.hhplus.server.domain.payment.message;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import io.hhplus.server.domain.payment.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentOutboxServiceTest {
    @Mock
    private PaymentOutboxRepository paymentOutboxRepository;

    @InjectMocks
    private PaymentOutboxService paymentOutboxService;

    @Test
    @DisplayName("메시지 발행 전 Outbox에 저장")
    void save_payment_outbox() {
        //given
        Payment payment = Payment.builder().id(1L).build();

        //when
        paymentOutboxService.savePaymentOutbox(payment);

        //then
        verify(paymentOutboxRepository, times(1)).save(any(PaymentOutbox.class));
    }

    @Test
    @DisplayName("메시지 발행 후 발행 보장을 위한 Outbox 업데이트")
    void update_payment_outbox_success() {
        //given
        Long paymentId = 1L;
        PaymentOutbox existingPaymentOutbox = PaymentOutbox.builder()
                                                           .id(1L)
                                                           .paymentId(paymentId)
                                                           .status(PaymentOutbox.Status.INIT)
                                                           .build();
        when(paymentOutboxRepository.findByPaymentId(paymentId)).thenReturn(Optional.of(existingPaymentOutbox));

        //when
        paymentOutboxService.updatePaymentOutbox(paymentId);

        //then
        verify(paymentOutboxRepository, times(1)).save(any(PaymentOutbox.class));
    }

    @Test
    @DisplayName("메시지 발행 후 발행 보장을 위한 Outbox 업데이트 - 실패")
    void update_payment_outbox_not_found() {
        //given
        Long paymentId = 1L;
        when(paymentOutboxRepository.findByPaymentId(paymentId)).thenReturn(Optional.empty());

        //when & then
        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> paymentOutboxService.updatePaymentOutbox(paymentId),
                "아웃박스에 존재하지 않는 결제건"
        );

        assertEquals(ErrorCode.PAYMENT_OUTBOX_NOT_FOUND, thrown.getErrorCode());
    }

    @Test
    @DisplayName("재발행 대상 메시지 목록 조회")
    void get_messages_for_retry() {
        // given
        LocalDateTime fixedTime = LocalDateTime.now().minusMinutes(3);
        PaymentOutbox paymentOutbox = PaymentOutbox.builder()
                                                   .paymentId(1L)
                                                   .status(PaymentOutbox.Status.INIT)
                                                   .createdAt(fixedTime.minusMinutes(1))
                                                   .updatedAt(fixedTime.minusMinutes(1))
                                                   .build();

        when(paymentOutboxRepository.getMessagesForRetry(
                eq(PaymentOutbox.Status.INIT.name()), any(LocalDateTime.class)))
                .thenAnswer(invocation -> {
                    LocalDateTime cutoffTime = invocation.getArgument(1);
                    if (cutoffTime.isBefore(fixedTime.minusMinutes(2))) {
                        return List.of(paymentOutbox);
                    } else {
                        return List.of();
                    }
                });

        // when
        List<PaymentOutbox> result = paymentOutboxService.getMessagesForRetry();

        // then
        assertNotNull(result);
    }
}