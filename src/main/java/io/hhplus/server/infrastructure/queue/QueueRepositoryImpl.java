package io.hhplus.server.infrastructure.queue;

import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {
    private final QueueJpaRepository queueRepository;

    @Override
    public Optional<Queue> getQueue(long userId, String token) {
        return Optional.empty();
    }

    @Override
    public Queue save(Queue queue) {
        return null;
    }

    @Override
    public long getLastActiveUserTokenId() {
        return 0;
    }

    @Override
    public List<Queue> findAllByStatusIsAndActivatedAtBefore(String status, LocalDateTime validationTime) {
        return queueRepository.findAllByStatusIsAndActivatedAtBefore(status, validationTime);
    }

    @Override
    public long countAllByStatusIs(String name) {
        return queueRepository.countAllByStatusIs(name);
    }

    @Override
    public List<Queue> findAllByStatusIsAndIdGreaterThanOrderByIdAsc(String status, long lastActiveUserTokenId, PageRequest pageRequest) {
        return queueRepository.findAllByStatusIsAndIdGreaterThanOrderByIdAsc(status, lastActiveUserTokenId, pageRequest);
    }
}