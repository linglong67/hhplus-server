package io.hhplus.server.interfaces.presentation.concert;

import io.hhplus.server.application.concert.ConcertFacade;
import io.hhplus.server.interfaces.presentation.concert.dto.ConcertSeatResponse;
import io.hhplus.server.interfaces.presentation.concert.dto.ConcertResponse;
import io.hhplus.server.interfaces.presentation.concert.dto.ConcertScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
@RequiredArgsConstructor
@Tag(name = "콘서트", description = "콘서트 정보 조회를 위한 API")
public class ConcertController {
    private final ConcertFacade concertFacade;

    @GetMapping
    @Operation(summary = "콘서트 목록 조회", description = "콘서트 목록을 조회하는 API")
    public List<ConcertResponse> getConcerts(
            @RequestHeader("Queue-Token") String token, @RequestBody long userId) {
        return concertFacade.getConcerts(userId, token)
                            .stream()
                            .map(ConcertResponse::toResponse)
                            .toList();
    }

    @GetMapping("{concertId}/available-dates")
    @Operation(summary = "예약 가능 날짜 조회", description = "특정 콘서트의 예약 가능한 날짜 목록을 조회하는 API")
    public List<ConcertScheduleResponse> getAvailableDates(
            @RequestHeader("Queue-Token") String token, @RequestBody long userId,
            @PathVariable("concertId") long concertId) {
        return concertFacade.getAvailableDates(concertId, userId, token)
                            .stream()
                            .map(ConcertScheduleResponse::toResponse)
                            .toList();
    }

    @GetMapping("{concertId}/schedules/{concertScheduleId}/available-seats")
    @Operation(summary = "예약 가능 좌석 조회", description = "특정 콘서트 일정의 예약 가능한 좌석 목록을 조회하는 API")
    public List<ConcertSeatResponse> getAvailableSeats(
            @RequestHeader("Queue-Token") String token, @RequestBody long userId,
            @PathVariable("concertId") long concertId,
            @PathVariable("concertScheduleId") long concertScheduleId) {
        return concertFacade.getAvailableSeats(concertId, concertScheduleId, userId, token)
                            .stream()
                            .map(ConcertSeatResponse::toResponse)
                            .toList();
    }
}
