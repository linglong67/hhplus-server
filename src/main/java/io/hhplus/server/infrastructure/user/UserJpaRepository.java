package io.hhplus.server.infrastructure.user;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserEntity u WHERE u.id = :userId")
    Optional<UserEntity> findByIdWithPessimisticLock(long userId);
}
