package io.hhplus.server.interfaces.interceptor;

import io.hhplus.server.application.queue.QueueFacade;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class QueueVerificationInterceptor implements HandlerInterceptor {
    private final QueueFacade queueFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String queueToken = request.getHeader("Queue-Token");
        queueFacade.verifyQueue(queueToken);

        return true;
    }
}