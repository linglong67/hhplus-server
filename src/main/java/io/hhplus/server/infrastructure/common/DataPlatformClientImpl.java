package io.hhplus.server.infrastructure.common;

import io.hhplus.server.domain.common.DataPlatformClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataPlatformClientImpl implements DataPlatformClient {

    @Override
    public void sendPaymentResult(Long paymentId) {
        log.info("데이터 플랫폼에 예약 결과 전송 시작");
    }
}
