package io.hhplus.server.integration.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaConsumer {
    private final CountDownLatch latch = new CountDownLatch(1);
    private String lastReceivedMessage;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void receive(ConsumerRecord<String, String> record) {
        this.lastReceivedMessage = record.value();
        System.out.println("######### Received Message: " + lastReceivedMessage);

        latch.countDown();
    }

    public String getLastReceivedMessage() throws InterruptedException {
        latch.await(3, TimeUnit.SECONDS);
        return lastReceivedMessage;
    }
}
