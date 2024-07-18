package io.hhplus.server.domain.concert;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                                      Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(concertSeatId);
                                      if(concertSeat.isEmpty()) {
                                          throw new IllegalArgumentException("존재하지 않는 좌석");
                                      }

                                      ConcertSeat seat = concertSeat.get();
                                      seat.assign();
                                      return concertRepository.update(seat);
                                  } catch (ObjectOptimisticLockingFailureException e) {
                                      throw new IllegalStateException("이미 선택된 좌석");
                                  }
                              })
                              .toList();

        if (assignedSeats.size() != concertSeatIds.size()) {
            throw new IllegalStateException("존재하지 않거나 이미 선택된 좌석이 포함되어 있음");
        }
    }

    public void releaseSeatHolds(List<Long> releaseTargets) {
        for (Long releaseTarget : releaseTargets) {
            Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(releaseTarget);

            if (concertSeat.isPresent()) {
                ConcertSeat seat = concertSeat.get();
                seat.release();
                concertRepository.update(seat);
            }
        }
    }

    public Optional<ConcertSeat> findConcertSeatById(Long concertSeatId) {
        return concertRepository.findConcertSeat(concertSeatId);
    }
}
