package com.transaction.consumer.listener;

public class KafkaListener {
    @org.springframework.kafka.annotation.KafkaListener(topics = "transactions", groupId = "G1")
    public void listen(String message) {
        System.out.println("Received Message in group G1: " + message);
    }
}
