package io.hhplus.server.domain.concert;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
                                  Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(concertSeatId);
                                  if (concertSeat.isEmpty()) {
                                      throw new BusinessException(ErrorCode.CONCERT_SEAT_NOT_FOUND);
                                  }

                                  ConcertSeat seat = concertSeat.get();
                                  seat.assign();
                                  return concertRepository.update(seat);
                              })
                              .toList();

        if (assignedSeats.size() != concertSeatIds.size()) {
            throw new BusinessException(ErrorCode.CONCERT_SEAT_NOT_AVAILABLE);
        }
    }

    public void releaseSeatHolds(List<Long> releaseTargets) {
        releaseTargets.forEach(releaseTarget -> {
            Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(releaseTarget);

            if (concertSeat.isPresent()) {
                ConcertSeat seat = concertSeat.get();
                seat.release();
                concertRepository.update(seat);
            }
        });
    }

    public ConcertInfo getConcertInfo(long concertScheduleId) {
        Optional<ConcertSchedule> concertSchedule = concertRepository.findConcertSchedule(concertScheduleId);

        if (concertSchedule.isEmpty()) {
            throw new BusinessException(ErrorCode.CONCERT_SCHEDULE_NOT_FOUND);
        }

        Optional<Concert> concert = concertRepository.findConcert(concertSchedule.get().getConcertId());
        Optional<Place> place = concertRepository.findPlace(concertSchedule.get().getPlaceId());

        return ConcertInfo.createConcertInfo(concert.get(), concertSchedule.get(), place.get());
    }

    public List<ConcertSeatInfo> getConcertSeatInfo(List<Long> concertSeatIds) {
        return concertSeatIds.stream()
                             .map(concertSeatId -> {
                                 Optional<ConcertSeat> concertSeat = concertRepository.findConcertSeat(concertSeatId);
                                 Optional<Place.Seat> seat = concertRepository.findSeat(concertSeat.get().getSeatId());

                                 return ConcertSeatInfo.createConcertSeatInfo(concertSeat.get(), seat.get());
                             })
                             .toList();
    }
}
