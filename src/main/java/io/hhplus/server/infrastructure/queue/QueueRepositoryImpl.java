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
        return queueRepository.findByUserIdAndToken(userId, token);
    }

    @Override
    public Queue save(Queue queue) {
        QueueEntity entity = QueueEntity.from(queue);
        return QueueEntity.toDomain(queueRepository.save(entity));
    }

    @Override
    public long getLastActiveUserTokenId() {
        return 0;
    }

    @Override
    public List<Queue> findAllByStatusIsAndActivatedAtBefore(Queue.Status status, LocalDateTime validationTime) {
        return queueRepository.findAllByStatusIsAndActivatedAtBefore(status, validationTime);
    }

    @Override
    public long countAllByStatusIs(Queue.Status status) {
        return queueRepository.countAllByStatusIs(status);
    }

    @Override
    public List<Queue> findAllByStatusIsAndIdGreaterThanOrderByIdAsc(Queue.Status status, long lastActiveUserTokenId, PageRequest pageRequest) {
        return queueRepository.findAllByStatusIsAndIdGreaterThanOrderByIdAsc(status, lastActiveUserTokenId, pageRequest);
    }

    @Override
    public void deleteAll() {
        queueRepository.deleteAll();
    }

    @Override
    public Optional<Queue> findById(Long id) {
        return queueRepository.findById(id).map(QueueEntity::toDomain);
    }
}