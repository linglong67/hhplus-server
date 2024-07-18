package io.hhplus.server.domain.user;

import io.hhplus.server.domain.common.exception.BusinessException;
import io.hhplus.server.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
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
        User user =  getUser(userId);
        user.chargePoint(amount);

        return userRepository.save(user);
    }

    @Transactional
    public User usePoint(long userId, int amount) {
        User user =  getUser(userId);
        user.usePoint(amount);

        return userRepository.save(user);
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }
}
