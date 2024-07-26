package io.hhplus.server.domain.user;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getPoint(long userId) {
        return getUser(userId);
    }

    @Transactional
    public User chargePoint(long userId, int amount) {
        User user = getUser(userId);

        int attempt = 0;
        while (attempt < 5) {
            try {
                user.chargePoint(amount);
                return userRepository.save(user);
            } catch (Exception e) {
                attempt++;
                if (attempt >= 5) {
                    throw new RuntimeException("재시도 그만!");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return User.builder().build();
    }

    public User usePoint(long userId, int amount) {
        User user = getUser(userId);

        int attempt = 0;
        while (attempt < 5) {
            try {
                user.usePoint(amount);
                return userRepository.save(user);
            } catch (Exception e) {
                attempt++;
                if (attempt >= 5) {
                    throw new RuntimeException("재시도 그만!");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return User.builder().build();
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
