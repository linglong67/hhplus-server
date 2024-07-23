package io.hhplus.server.integration;

import io.hhplus.server.application.user.UserDto;
import io.hhplus.server.application.user.UserFacade;
import io.hhplus.server.domain.user.User;
import io.hhplus.server.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserIntegrationTest {
    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder().name("test").point(1000).build();
        testUser = userRepository.save(testUser);
    }

    @Test
    @DisplayName("포인트 조회")
    void getPoint() {
        UserDto userDto = userFacade.getPoint(testUser.getId());
        assertThat(userDto).isNotNull();
        assertThat(userDto.getPoint()).isEqualTo(1000);
    }

    @Test
    @DisplayName("포인트 충전")
    void chargePoint() {
        UserDto userDto = userFacade.chargePoint(testUser.getId(), 500);
        assertThat(userDto).isNotNull();
        assertThat(userDto.getPoint()).isEqualTo(1500);
    }

    @Test
    @DisplayName("포인트 사용")
    void usePoint() {
        UserDto userDto = userFacade.usePoint(testUser.getId(), 200);
        assertThat(userDto).isNotNull();
        assertThat(userDto.getPoint()).isEqualTo(800);
    }
}