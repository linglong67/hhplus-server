package io.hhplus.server.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getPoint(long userId) {
        return getUser(userId);
    }

    public User chargePoint(long userId, int amount) {
        User user =  getUser(userId);

        validateAmount(amount);
        user.chargePoint(amount);

        return userRepository.save(user);
    }


    public User usePoint(long userId, int amount) {
        User user =  getUser(userId);

        if (user.getPoint() < amount) {
            throw new IllegalArgumentException("잔액 초과하여 사용 불가");
        }
        validateAmount(amount);
        user.usePoint(amount);

        return userRepository.save(user);
    }

    private User getUser(long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 ID"));
    }

    private void validateAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전/사용 포인트가 0 이하");
        }
    }
}
