package io.hhplus.server.presentation.payment.controller;

import io.hhplus.server.presentation.payment.dto.PaymentRequest;
import io.hhplus.server.presentation.payment.dto.PaymentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping
    public PaymentResponse payment(@RequestBody PaymentRequest request) {
        return new PaymentResponse(1L, LocalDateTime.now(), "COMPLETED");
    }
}