package io.hhplus.server.application.concert;

import io.hhplus.server.domain.concert.Concert;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConcertDto {
    private long concertId;
    private String title;
    private String casting;

    public static ConcertDto toDto(Concert concert) {
        return ConcertDto.builder()
                         .concertId(concert.getId())
                         .title(concert.getTitle())
                         .casting(concert.getCasting())
                         .build();
    }
}