package io.hhplus.server.integration;

import io.hhplus.server.application.user.PointDto;
import io.hhplus.server.application.user.UserFacade;
import io.hhplus.server.domain.user.User;
import io.hhplus.server.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
    void getPoint() {
        PointDto pointDto = userFacade.getPoint(testUser.getId());
        assertThat(pointDto).isNotNull();
        assertThat(pointDto.getPoint()).isEqualTo(1000);
    }

    @Test
    void chargePoint() {
        PointDto pointDto = userFacade.chargePoint(testUser.getId(), 500);
        assertThat(pointDto).isNotNull();
        assertThat(pointDto.getPoint()).isEqualTo(1500);
    }

    @Test
    void usePoint() {
        PointDto pointDto = userFacade.usePoint(testUser.getId(), 200);
        assertThat(pointDto).isNotNull();
        assertThat(pointDto.getPoint()).isEqualTo(800);
    }
}