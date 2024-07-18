package io.hhplus.server.config;

import io.hhplus.server.interfaces.interceptor.QueueVerificationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final QueueVerificationInterceptor queueVerificationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queueVerificationInterceptor)
                .addPathPatterns("/api/concerts/**")
                .addPathPatterns("/api/payments/**")
                .addPathPatterns("/api/reservations/**");
    }
}