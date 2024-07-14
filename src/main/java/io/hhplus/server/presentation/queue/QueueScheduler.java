package io.hhplus.server.presentation.queue;

import io.hhplus.server.application.queue.QueueFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueScheduler {
    private final QueueFacade queueFacade;

    @Scheduled(fixedDelay = 3000)
    public void activateTokens() {
        queueFacade.activateTokens();
    }

    @Scheduled(fixedDelay = 10000)
    public void expireTokens() {
        queueFacade.expireTokens();
    }
}
