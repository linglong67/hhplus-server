package io.hhplus.server.domain.concert;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Place {
    private Long id;
    private String name;
    private List<Seat> seats;

    @Getter
    @Builder
    public static class Seat {
        private Long id;
        private Integer seatNo;
    }
}
