package io.hhplus.server.presentation.balance.dto;

public record BalanceRequest(
        long userId,
        long amount
) {
}
