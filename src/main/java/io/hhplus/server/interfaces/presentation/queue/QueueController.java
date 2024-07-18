package io.hhplus.server.interfaces.presentation.queue;

import io.hhplus.server.application.queue.QueueFacade;
import io.hhplus.server.interfaces.presentation.queue.dto.QueueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
@Tag(name = "대기열", description = "대기열 관리를 위한 API")
public class QueueController {
    private final QueueFacade queueFacade;

    @PostMapping
    @Operation(summary = "대기열 토큰 발급", description = "사이트 접속 시 대기열 토큰을 발급하는 API")
    public QueueResponse generateToken(@RequestBody long userId) {
        return QueueResponse.toResponse(queueFacade.generateToken(userId));
    }

    @GetMapping
    @Operation(summary = "대기열 정보 조회", description = "특정 사용자의 대기열 정보를 조회하는 API")
    public QueueResponse getQueueInfo(@RequestHeader("Queue-Token") String token, @RequestBody long userId) {
        return QueueResponse.toResponse(queueFacade.getQueueInfo(userId, token));
    }
}