package io.hhplus.server.infrastructure.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class QueueRedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    private static final String QUEUE = "queue";
    private static final String ACTIVE_TOKEN = "active-token";
    private static final int MAX_ACTIVE_MINUTES = 30;
    private static final int MAX_QUEUE_SIZE = 50;

    // 대기열에 추가
    public void addQueue(String token) {
        redisTemplate.opsForZSet().add(QUEUE, token, System.currentTimeMillis());
    }

    // 대기열 순서 조회
    public Long getQueueOrder(String token) {
        return redisTemplate.opsForZSet().rank(QUEUE, token);
    }

    // 토큰 검증
    public Boolean verifyToken(String tokenWithTime) {
        return redisTemplate.opsForSet().isMember(ACTIVE_TOKEN, tokenWithTime);
    }

    // 토큰 만료
    public void expireToken(String tokenWithTime) {
        redisTemplate.opsForSet().remove(ACTIVE_TOKEN, tokenWithTime);
    }

    // [다건] 토큰 활성화
    public void activateTokens() {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        SetOperations<String, String> setOps = redisTemplate.opsForSet();

        Set<String> members = setOps.members(ACTIVE_TOKEN);
        int currentEntries = members == null ? 0 : members.size();
        int targetCount = MAX_QUEUE_SIZE - currentEntries;

        Set<String> tokens = zSetOps.range(QUEUE, 0, targetCount);
        redisTemplate.executePipelined((RedisCallback<Void>)conn -> {
            conn.openPipeline();
            if (tokens == null) return null;

            zSetOps.remove(QUEUE, tokens.toArray());
            tokens.forEach(token -> setOps.add(ACTIVE_TOKEN,
                    String.join(":", token, String.valueOf(System.currentTimeMillis()))));

            conn.closePipeline();
            return null;
        });
    }

    // [다건] 토큰 만료 처리
    public void expireTokens() {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        Set<String> members = setOps.members(ACTIVE_TOKEN);
        if (members == null) return;

        long currentTime = System.currentTimeMillis();
        members.forEach(tokenWithTime -> {
            if (currentTime - Long.parseLong(tokenWithTime.split(":")[1]) > MAX_ACTIVE_MINUTES * 60 * 1000) {
                setOps.remove(ACTIVE_TOKEN, tokenWithTime);
            }
        });
    }
}
