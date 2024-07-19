package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ConcertInfo {
    private Long concertId;
    private String concertTitle;
    private String concertCasting;

    private Long concertScheduleId;
    private LocalDateTime concertDatetime;

    private Long placeId;
    private String placeName;

    public static ConcertInfo createConcertInfo(Concert concert, ConcertSchedule concertSchedule, Place place) {
        return ConcertInfo.builder()
                          .concertId(concert.getId())
                          .concertTitle(concert.getTitle())
                          .concertCasting(concert.getCasting())
                          .concertScheduleId(concertSchedule.getId())
                          .concertDatetime(concertSchedule.getConcertDatetime())
                          .placeId(place.getId())
                          .placeName(place.getName())
                          .build();
    }
}
