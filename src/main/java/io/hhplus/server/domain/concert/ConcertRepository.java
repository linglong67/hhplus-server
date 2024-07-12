package io.hhplus.server.domain.concert;

import java.util.List;

public interface ConcertRepository {
    List<Concert> findAll();

    List<ConcertSchedule> getAvailableDates(long concertId);

    List<ConcertSeat> getAvailableSeats(long concertId, long concertScheduleId);

    ConcertSeat assignSeat(Long concertSeatId);

    void update(ConcertSeat concertSeat);

    ConcertSeat findConcertSeat(Long releaseTarget);
}