package io.hhplus.server.service;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueRepository;
import io.hhplus.server.domain.queue.QueueService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueServiceTest {
    @InjectMocks
    private QueueService queueService;

    @Mock
    private QueueRepository queueRepository;

    @Test
    void generateToken_success() {
        //given
        long userId = 1L;
        Queue queue = Queue.builder().build();
        when(queueRepository.save(any(Queue.class))).thenReturn(queue);

        //when
        Queue savedQueue = queueService.generateToken(userId);

        //then
        assertThat(savedQueue).isEqualTo(queue);
    }

    @Test
    void getQueueOrder_success() {
        //given
        long userId = 1L;
        String token = "token";
        Queue queue = Queue.builder().id(10L).build();
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.of(queue));
        when(queueRepository.getLastActiveUserTokenId()).thenReturn(5L);

        //when
        long order = queueService.getQueueOrder(userId, token);

        //then
        assertThat(order).isEqualTo(5L); // 10 - 5
    }

    @Test
    void getQueueOrder_failure_invalidToken() {
        //given
        long userId = 1L;
        String token = "invalidToken";
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> queueService.getQueueOrder(userId, token))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 토큰 정보");
    }

    @Test
    void getQueueInfo_success() {
        //given
        long userId = 1L;
        String token = "token";
        Queue queue = Queue.builder().build();
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.of(queue));

        //when
        Queue queueInfo = queueService.getQueueInfo(userId, token);

        //then
        assertThat(queueInfo).isEqualTo(queue);
    }

    @Test
    void verifyQueue_success() {
        //given
        long userId = 1L;
        String token = "token";
        Queue queue = Queue.builder().build();
        queue.activate(); // 상태를 ACTIVE로 설정
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.of(queue));

        //when & then
        assertThatCode(() -> queueService.verifyQueue(userId, token)).doesNotThrowAnyException();
    }

    @Test
    void verifyQueue_failure_inactiveToken() {
        //given
        long userId = 1L;
        String token = "token";
        Queue queue = Queue.builder().build();
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.of(queue));

        //when & then
        assertThatThrownBy(() -> queueService.verifyQueue(userId, token))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("활성 토큰이 아님");
    }

    @Test
    void expireToken_success() {
        //given
        long userId = 1L;
        String token = "token";
        Queue queue = Queue.builder().build();
        when(queueRepository.getQueue(userId, token)).thenReturn(Optional.of(queue));

        //when
        queueService.expireToken(userId, token);

        //then
        verify(queueRepository).save(queue);
        assertThat(queue.getStatus()).isEqualTo(Queue.Status.EXPIRED);
    }

    @Test
    void findUsersToActivate_success() {
        //given
        int currentEntries = 10;
        long lastActiveUserTokenId = 5L;
        List<Queue> expectedQueues = List.of(Queue.builder().build(), Queue.builder().build());

        when(queueRepository.countAllByStatusIs(Queue.Status.ACTIVE.name())).thenReturn((long) currentEntries);
        when(queueRepository.getLastActiveUserTokenId()).thenReturn(lastActiveUserTokenId);
        when(queueRepository.findAllByStatusIsAndIdGreaterThanOrderByIdAsc(
                eq(Queue.Status.WAITING.name()), eq(lastActiveUserTokenId), any(PageRequest.class)
        )).thenReturn(expectedQueues);

        //when
        List<Queue> actualQueues = queueService.findUsersToActivate();

        //then
        assertThat(actualQueues).isEqualTo(expectedQueues);
    }

    @Test
    void activateTokens_success() {
        //given
        List<Queue> queueList = List.of(mock(Queue.class), mock(Queue.class));

        //when
        queueService.activateTokens(queueList);

        //then
        for (Queue queue : queueList) {
            verify(queue).activate();
            verify(queueRepository).save(queue);
        }
    }

    @Test
    void findActiveUsersForMoreThan30Minutes_success() {
        //given
        List<Queue> expectedQueues = List.of(Queue.builder().build(), Queue.builder().build());
        when(queueRepository.findAllByStatusIsAndActivatedAtBefore(
                eq(Queue.Status.ACTIVE.name()), any(LocalDateTime.class)
        )).thenReturn(expectedQueues);

        //when
        List<Queue> actualQueues = queueService.findActiveUsersForMoreThan30Minutes();

        //then
        assertThat(actualQueues).isEqualTo(expectedQueues);
    }

    @Test
    void expireTokens_success() {
        //given
        List<Queue> queueList = List.of(mock(Queue.class), mock(Queue.class));

        //when
        queueService.expireTokens(queueList);

        //then
        for (Queue queue : queueList) {
            verify(queue).expire();
            verify(queueRepository).save(queue);
        }
    }
}