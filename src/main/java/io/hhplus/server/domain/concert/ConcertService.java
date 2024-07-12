package io.hhplus.server.domain.concert;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertRepository concertRepository;

    public List<Concert> getConcerts() {
        return concertRepository.findAll();
    }

    public List<ConcertSchedule> getAvailableDates(long concertId) {
        return concertRepository.getAvailableDates(concertId);
    }

    public List<ConcertSeat> getAvailableSeats(long concertId, long concertScheduleId) {
        return concertRepository.getAvailableSeats(concertId, concertScheduleId);
    }

    public void assignSeats(List<Long> concertSeatIds) {
        List<ConcertSeat> assignedSeats =
                concertSeatIds.stream()
                              .map(concertSeatId -> {
                                  try {
                                      return concertRepository.assignSeat(concertSeatId);
                                  } catch (ObjectOptimisticLockingFailureException e) {
                                      throw new IllegalStateException("이미 선택된 좌석");
                                  }
                              })
                              .toList();

        if (assignedSeats.size() != concertSeatIds.size()) {
            throw new IllegalStateException("이미 선택된 좌석이 포함되어 있음");
        }
    }
}
