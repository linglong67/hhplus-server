package io.hhplus.server.domain.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long userId);

    User save(User user);
}