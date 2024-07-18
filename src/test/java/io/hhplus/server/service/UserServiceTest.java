package io.hhplus.server.service;

import io.hhplus.server.domain.user.User;
import io.hhplus.server.domain.user.UserRepository;
import io.hhplus.server.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("포인트 조회")
    void getPoint_success() {
        //given
        Long userId = 1L;
        String userName = "test";
        Integer userPoint = 10_000;

        User expected = User.builder()
                            .id(userId)
                            .name(userName)
                            .point(userPoint)
                            .build();

        //when
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(expected));

        //then
        assertThat(userService.getPoint(userId)).isEqualTo(expected);
    }

    @Test
    @DisplayName("포인트 충전")
    void chargePoint_success() {
        //given
        long userId = 1L;
        int amount = 100;
        User user = User.builder().id(userId).point(0).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        User result = userService.chargePoint(userId, amount);

        //then
        verify(userRepository).save(user);
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("포인트 충전 실패")
    void chargePoint_failure_invalidAmount() {
        //given
        long userId = 1L;
        int amount = -100;

        //when & then
        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().build()));
        assertThatThrownBy(() -> userService.chargePoint(userId, amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("충전/사용 포인트가 0 이하");
    }

    @Test
    @DisplayName("포인트 사용")
    void usePoint_success() {
        //given
        long userId = 1L;
        int amount = 50;
        User user = User.builder().id(userId).point(amount).build();
        user.chargePoint(100);

        //when
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        User result = userService.usePoint(userId, amount);

        //then
        verify(userRepository).save(user);
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("포인트 사용 실패")
    void usePoint_failure_insufficientBalance() {
        //given
        long userId = 1L;
        int amount = 150;
        User user = User.builder().id(userId).point(0).build();

        user.chargePoint(100);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //when & then
        assertThatThrownBy(() -> userService.usePoint(userId, amount))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("잔액 초과하여 사용 불가");
    }

    @Test
    @DisplayName("존재하지 않는 사용자 예외 처리")
    void getUser_failure_nonexistentUser() {
        //given
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> userService.getPoint(userId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자 ID");
    }
}