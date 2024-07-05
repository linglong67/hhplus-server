package io.hhplus.server.presentation.balance.controller;

import io.hhplus.server.presentation.balance.dto.BalanceRequest;
import io.hhplus.server.presentation.balance.dto.BalanceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    @GetMapping("{id}")
    public BalanceResponse balance(@PathVariable("id") long userId) {
        return new BalanceResponse(1L, 10_000L);
    }

    @PatchMapping("charge")
    public BalanceResponse charge(@RequestBody BalanceRequest request) {
        return new BalanceResponse(1L, 15_000L);
    }
}