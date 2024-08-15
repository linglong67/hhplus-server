package io.hhplus.server.integration.kafka;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class KafkaIntegrationTest {
    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    @Test
    @DisplayName("Kafka 연결 테스트")
    void testKafkaIntergaration() throws InterruptedException {
        String topic = "test-topic";
        String message = "Hello~!";

        producer.sendMessage(topic, message);
        assertThat(message).isEqualTo(consumer.getLastReceivedMessage());
    }
}
