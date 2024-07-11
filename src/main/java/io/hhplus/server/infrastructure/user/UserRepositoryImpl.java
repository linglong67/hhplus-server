package io.hhplus.server.infrastructure.user;

import io.hhplus.server.domain.user.User;
import io.hhplus.server.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userRepository;

    @Override
    public Optional<User> findById(long userId) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }
}