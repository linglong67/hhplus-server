package io.hhplus.server.integration;

import io.hhplus.server.application.payment.PaymentDto;
import io.hhplus.server.application.payment.PaymentFacade;
import io.hhplus.server.domain.payment.Payment;
import io.hhplus.server.domain.payment.PaymentRepository;
import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PaymentIntegrationTest {

    @Autowired
    private PaymentFacade paymentFacade;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private QueueService queueService;

    private Queue testQueue;

    @BeforeEach
    void setUp() {
        testQueue = queueService.addQueue(2L);
    }

    @Test
    @DisplayName("결제")
    void payment() {
        //given
        long userId = 2L;
        long reservationId = 2L;
        PaymentDto dto = PaymentDto.builder()
                                   .userId(userId)
                                   .reservationId(reservationId)
                                   .paidPrice(10000)
                                   .build();

        //when
        PaymentDto result = paymentFacade.payment(testQueue.getToken(), dto);

        //then
        Payment payment = paymentRepository.getPayment(result.getId())
                                           .orElseThrow(() -> new AssertionError("Payment not found"));

        assertThat(payment.getStatus()).isEqualTo(Payment.Status.PAID);
        assertThat(payment.getPaidPrice()).isEqualTo(dto.getPaidPrice());
        assertThat(payment.getReservationId()).isEqualTo(reservationId);
    }
}