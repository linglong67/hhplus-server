package io.hhplus.server.domain.concert;

import lombok.Getter;

import java.util.List;

@Getter
public class Place {
    private Long id;
    private String name;
    private List<Seat> seats;

    public class Seat {
        private Long id;
        private String seatNo;
    }
}
