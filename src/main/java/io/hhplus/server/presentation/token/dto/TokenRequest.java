package io.hhplus.server.presentation.token.dto;

public record TokenRequest(
        long userId,
        long concertId
) {
}
