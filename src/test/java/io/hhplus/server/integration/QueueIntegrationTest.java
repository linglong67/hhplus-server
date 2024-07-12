package io.hhplus.server.integration;

import io.hhplus.server.application.queue.QueueDto;
import io.hhplus.server.application.queue.QueueFacade;
import io.hhplus.server.domain.queue.Queue;
import io.hhplus.server.domain.queue.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class QueueIntegrationTest {

    @Autowired
    private QueueFacade queueFacade;

    @Autowired
    private QueueRepository queueRepository;

    private Queue testQueue;

    @BeforeEach
    void setUp() {
        queueRepository.deleteAll();  // 데이터베이스 초기화
        testQueue = Queue.builder()
                         .userId(1L)
                         .token("testToken")
                         .status(Queue.Status.WAITING)
                         .activatedAt(LocalDateTime.now())
                         .build();
        queueRepository.save(testQueue);  // 초기 큐 데이터 설정
    }

    @Test
    void generateToken() {
        //given
        long userId = 1L;

        //when
        QueueDto queueDto = queueFacade.generateToken(userId);

        //then
        assertNotNull(queueDto);
        assertNotNull(queueDto.getToken());
        assertEquals(userId, queueDto.getUserId());
        assertEquals(Queue.Status.WAITING, queueDto.getStatus());
    }

    @Test
    void getQueueInfo() {
        //given
        String token = testQueue.getToken();
        long userId = testQueue.getUserId();

        //when
        QueueDto queueDto = queueFacade.getQueueInfo(userId, token);

        //then
        assertNotNull(queueDto);
        assertEquals(userId, queueDto.getUserId());
        assertEquals(token, queueDto.getToken());
        assertEquals(Queue.Status.WAITING, queueDto.getStatus());
    }

    @Test
    void activateTokens() {
        //given
        Queue waitingQueue = Queue.builder()
                                  .userId(2L)
                                  .token("newToken")
                                  .status(Queue.Status.WAITING)
                                  .activatedAt(LocalDateTime.now().minusMinutes(31))
                                  .build();
        Queue queue = queueRepository.save(waitingQueue);

        //when
        queueFacade.activateTokens();

        //then
        Queue updatedQueue = queueRepository.findById(queue.getId()).orElse(null);
        assertNotNull(updatedQueue);
        assertEquals(Queue.Status.ACTIVE, updatedQueue.getStatus());
    }

    @Test
    void expireTokens() {
        //given
        Queue activeQueue = Queue.builder()
                                 .userId(3L)
                                 .token("activeToken")
                                 .status(Queue.Status.ACTIVE)
                                 .activatedAt(LocalDateTime.now().minusMinutes(31))
                                 .build();
        Queue quque = queueRepository.save(activeQueue);

        //when
        queueFacade.expireTokens();

        //then
        Queue updatedQueue = queueRepository.findById(quque.getId()).orElse(null);
        assertNotNull(updatedQueue);
        assertEquals(Queue.Status.EXPIRED, updatedQueue.getStatus());
    }
}