package io.hhplus.server.service;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueRepository;
import io.hhplus.server.domain.queue.QueueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueRepository queueRepository;

    @Test
    @DisplayName("토큰 생성")
    void addQueue_success() {
        //given
        long userId = 1L;

        //when
        Queue queue = queueService.addQueue(userId);

        //then
        assertThat(queue.getToken()).isNotNull();
    }

    @Test
    @DisplayName("대기열 순서 조회")
    void getQueueOrder_success() {
        //given
        String token = "token";
        Long expectedRank = 1L;
        when(queueRepository.getQueueOrder(token)).thenReturn(expectedRank);

        //when
        Long actualRank = queueService.getQueueOrder(token);

        //then
        assertThat(actualRank).isEqualTo(expectedRank);
    }

    @Test
    @DisplayName("대기열 순서 조회 - 유효하지 않은 토큰")
    void getQueueOrder_failure_invalidToken() {
        //given
        String token = "invalidToken";
        when(queueRepository.getQueueOrder(token)).thenReturn(null);

        //when & then
        assertThatThrownBy(() -> queueService.getQueueOrder(token))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("활성 토큰 검증 성공")
    void verifyToken_success() {
        //given
        String token = "token";
        when(queueRepository.verifyToken(token)).thenReturn(Boolean.TRUE);

        //when & then
        assertThatCode(() -> queueService.verifyToken(token)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("활성 토큰 검증 실패")
    void verifyToken_failure() {
        //given
        String token = "token";
        when(queueRepository.verifyToken(token)).thenReturn(Boolean.FALSE);

        //when & then
        assertThatThrownBy(() -> queueService.verifyToken(token))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("토큰 만료 (단건)")
    void expireToken_success() {
        //given
        String tokenWithTime = "token:timestamp";

        //when
        queueService.expireToken(tokenWithTime);

        //then
        verify(queueRepository).expireToken(tokenWithTime);
    }

    @Test
    @DisplayName("토큰 활성 (다건)")
    void activateTokens_success() {
        //given & when
        queueService.activateTokens();

        //then
        verify(queueRepository, times(1)).activateTokens();
    }

    @Test
    @DisplayName("토큰 만료 (다건)")
    void expireTokens_success() {
        //given & when
        queueService.expireTokens();

        //then
        verify(queueRepository, times(1)).expireTokens();
    }
}