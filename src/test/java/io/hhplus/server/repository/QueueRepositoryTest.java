package io.hhplus.server.repository;

import io.hhplus.server.infrastructure.queue.QueueRedisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QueueRepositoryTest {
    @InjectMocks
    private QueueRedisRepository queueRedisRepository;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    private SetOperations<String, String> setOps;
    private ZSetOperations<String, String> zSetOps;

    private static final String QUEUE = "queue";
    private static final String ACTIVE_TOKEN = "active-token";
    private static final int MAX_QUEUE_SIZE = 50;

    @BeforeEach
    void setUp() {
        setOps = mock(SetOperations.class);
        zSetOps = mock(ZSetOperations.class);
    }

    @Test
    @DisplayName("대기열에 토큰 정보 추가")
    void testAddQueue() {
        //given
        String token = "token1";
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);

        //when
        queueRedisRepository.addQueue(token);

        //then
        verify(zSetOps).add(eq(QUEUE), eq(token), anyDouble());
    }

    @Test
    @DisplayName("대기열에 있는 토큰의 순서 조회")
    void testGetQueueOrder() {
        //given
        String token = "token1";
        Long expectedRank = 1L;
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        when(zSetOps.rank(QUEUE, token)).thenReturn(expectedRank);

        //when
        Long rank = queueRedisRepository.getQueueOrder(token);

        //then
        verify(zSetOps).rank(QUEUE, token);
        assertEquals(expectedRank, rank);
    }

    @Test
    @DisplayName("활성 토큰 검증")
    void testVerifyToken() {
        //given
        String tokenWithTime = "token:1234567890";
        when(redisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.isMember(ACTIVE_TOKEN, tokenWithTime)).thenReturn(true);

        //when
        Boolean result = queueRedisRepository.verifyToken(tokenWithTime);

        //then
        verify(setOps).isMember(ACTIVE_TOKEN, tokenWithTime);
        assertTrue(result);
    }

    @Test
    @DisplayName("활성 토큰 만료 처리")
    void testExpireToken() {
        //given
        String tokenWithTime = "token:1234567890";
        when(redisTemplate.opsForSet()).thenReturn(setOps);

        //when
        queueRedisRepository.expireToken(tokenWithTime);

        //then
        verify(setOps).remove(ACTIVE_TOKEN, tokenWithTime);
    }

    @Test
    @DisplayName("토큰 활성화 - 다건")
    void testActivateTokens() {
        //given
        Set<String> activeTokens = new HashSet<>();
        Set<String> tokensToActivate = new HashSet<>();
        tokensToActivate.add("token1");
        tokensToActivate.add("token2");

        when(redisTemplate.opsForSet()).thenReturn(setOps);
        when(redisTemplate.opsForZSet()).thenReturn(zSetOps);
        when(setOps.members(ACTIVE_TOKEN)).thenReturn(activeTokens);
        when(zSetOps.range(QUEUE, 0, MAX_QUEUE_SIZE - activeTokens.size())).thenReturn(tokensToActivate);

        //when
        queueRedisRepository.activateTokens();

        //then
        verify(redisTemplate, times(1)).executePipelined(any(RedisCallback.class));
    }

    @Test
    @DisplayName("토큰 만료 - 다건")
    void testExpireTokens() {
        //given
        Set<String> tokensWithTime = new HashSet<>();
        tokensWithTime.add("token1:1234567890");
        tokensWithTime.add("token2:1234567890");

        when(redisTemplate.opsForSet()).thenReturn(setOps);
        when(setOps.members(ACTIVE_TOKEN)).thenReturn(tokensWithTime);

        //when
        queueRedisRepository.expireTokens();

        //then
        verify(setOps).remove(ACTIVE_TOKEN, "token1:1234567890");
        verify(setOps).remove(ACTIVE_TOKEN, "token2:1234567890");
    }
}