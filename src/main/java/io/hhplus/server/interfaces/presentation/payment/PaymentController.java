package io.hhplus.server.interfaces.presentation.payment;

import io.hhplus.server.application.payment.PaymentFacade;
import io.hhplus.server.interfaces.presentation.payment.dto.PaymentRequest;
import io.hhplus.server.interfaces.presentation.payment.dto.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "결제",description = "콘서트 예약의 결제를 위한 API")
public class PaymentController {
    private final PaymentFacade paymentFacade;

    @PostMapping
    @Operation(summary = "결제", description = "콘서트 예약건을 결제하는 API")
    public PaymentResponse payment(
            @RequestHeader("Queue-Token") String token,
            @RequestBody PaymentRequest request) {
        return PaymentResponse.toResponse(paymentFacade.payment(token, PaymentRequest.toDto(request)));
    }
}