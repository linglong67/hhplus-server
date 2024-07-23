package io.hhplus.server.domain.concert;

import java.util.List;
import java.util.Optional;

public interface ConcertRepository {
    List<Concert> findAll();

    List<ConcertSchedule> getAvailableDates(long concertId);

    List<ConcertSeat> getAvailableSeats(long concertId, long concertScheduleId);

    ConcertSeat update(ConcertSeat concertSeat);

    Optional<ConcertSeat> findConcertSeat(Long concertSeatId);

    Optional<Concert> findConcert(Long concertId);

    Optional<ConcertSchedule> findConcertSchedule(Long concertScheduleId);

    Optional<Place> findPlace(Long placeId);

    Optional<Place.Seat> findSeat(Long seatId);
}