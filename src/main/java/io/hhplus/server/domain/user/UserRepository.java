package io.hhplus.server.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long userId);

    Optional<User> findByIdWithPessimisticLock(long userId);

    User save(User user);
}