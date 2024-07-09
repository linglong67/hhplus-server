package io.hhplus.server.presentation.token.dto;

public record TokenResponse(
        long tokenId,
        String token,
        String status,
        long queueOrder
) {
}
