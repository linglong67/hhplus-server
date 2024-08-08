package io.hhplus.server.interfaces.scheduler;

import io.hhplus.server.application.queue.QueueFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueScheduler {
    private final QueueFacade queueFacade;

    @Scheduled(fixedDelay = 3 * 1000)
    public void activateTokens() {
        queueFacade.activateTokens();
    }

    @Scheduled(fixedDelay = 10 * 1000)
    public void expireTokens() {
        queueFacade.expireTokens();
    }
}
