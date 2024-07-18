package io.hhplus.server.service;

import io.hhplus.server.domain.payment.PaymentRepository;
import io.hhplus.server.domain.payment.PaymentService;
import io.hhplus.server.domain.payment.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    @DisplayName("결제 정보 저장")
    void savePayment_success() {
        //given
        Payment payment = Payment.builder().build();
        Payment savedPayment = Payment.builder().build();
        savedPayment.updateStatus(Payment.Status.PAID);

        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);

        //when
        Payment result = paymentService.savePayment(payment);

        //then
        verify(paymentRepository).save(payment);
        assertThat(result.getStatus()).isEqualTo(Payment.Status.PAID);
    }
}