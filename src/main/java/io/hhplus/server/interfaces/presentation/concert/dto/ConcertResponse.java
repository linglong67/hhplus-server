package io.hhplus.server.interfaces.presentation.concert.dto;

import io.hhplus.server.application.concert.ConcertDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertResponse {
    private long concertId;
    private String title;
    private String casting;

    public static ConcertResponse toResponse(ConcertDto dto) {
        return ConcertResponse.builder()
                              .concertId(dto.getConcertId())
                              .title(dto.getTitle())
                              .casting(dto.getCasting())
                              .build();
    }
}
