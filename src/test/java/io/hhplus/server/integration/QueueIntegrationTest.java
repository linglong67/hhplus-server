package io.hhplus.server.integration;

import io.hhplus.server.application.queue.QueueDto;
import io.hhplus.server.application.queue.QueueFacade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class QueueIntegrationTest {
    @Autowired
    private QueueFacade queueFacade;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String QUEUE = "queue";
    private static final String ACTIVE_TOKEN = "active-token";

    @AfterEach
    void tearDown() {
        redisTemplate.delete(QUEUE);
        redisTemplate.delete(ACTIVE_TOKEN);
    }

    @Test
    @DisplayName("토큰 발급 (생성)")
    void addQueue() {
        //given & when
        QueueDto queueDto = queueFacade.addQueue(1L);

        //then
        assertNotNull(queueDto);
        assertNotNull(queueDto.getToken());
    }

    @Test
    @DisplayName("토큰 정보 조회")
    void getQueueOrder() {
        //given
        queueFacade.addQueue(1L);
        queueFacade.addQueue(2L);

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        String token = zSetOps.randomMember(QUEUE);

        //when
        QueueDto queueDto = queueFacade.getQueueOrder(token);

        //then
        assertThat(queueDto.getToken()).isEqualTo(token);
    }

    @Test
    @DisplayName("토큰 활성화")
    void activateTokens() {
        //given
        queueFacade.addQueue(1L);
        queueFacade.addQueue(2L);

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        Long queueSize = zSetOps.size(QUEUE);
        Long activeTokenSize = setOps.size(ACTIVE_TOKEN);

        //when
        queueFacade.activateTokens();

        //then
        assertThat(zSetOps.size(QUEUE)).isLessThan(queueSize);
        assertThat(setOps.size(ACTIVE_TOKEN)).isGreaterThan(activeTokenSize);
    }

    @Test
    @DisplayName("토큰 만료")
    void expireTokens() {
        //given
        long currentTime = System.currentTimeMillis();
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        setOps.add(ACTIVE_TOKEN, "testToken1:" + (currentTime - 30 * 60 * 1000));
        setOps.add(ACTIVE_TOKEN, "testToken2:" + currentTime);

        //when
        queueFacade.expireTokens();

        //then
        assertThat(setOps.isMember(ACTIVE_TOKEN, "testToken1:" + (currentTime - 30 * 60 * 1000))).isFalse();
        assertThat(setOps.isMember(ACTIVE_TOKEN, "testToken2:" + currentTime)).isTrue();
    }
}