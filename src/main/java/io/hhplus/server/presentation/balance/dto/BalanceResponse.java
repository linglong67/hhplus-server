package io.hhplus.server.presentation.balance.dto;

public record BalanceResponse(
        long userId,
        long balance
) {
}
