package com.transaction.producer.dispatcher;

import com.fasterxml.uuid.Generators;
import com.transaction.producer.KafkaConfigurations;
import com.transaction.producer.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Dispatch {
    private static Logger log = LoggerFactory.getLogger(Dispatch.class);

    @Autowired
    private KafkaConfigurations kConfig;
    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Scheduled(fixedRateString = "${transaction.generation.rate}")
    public void dispatchToKafka() {
        try {
            String isApproved = Math.random() < 0.5 ? "Approved" : "Rejected";
            Transaction transaction = new Transaction(Generators.timeBasedGenerator().generate().toString(),
                    isApproved, kConfig.getClient());
            String topic = kConfig.getKafkaTopic();
            log.info("Transaction Generated: {} for kafka topic: {}", transaction.toString(), topic);
            dispatchPayloadToKafka(transaction, topic);
        } catch (Exception e) {
            log.info("An Exception has occured.");
        }
    }

    public void dispatchPayloadToKafka(Transaction transaction, String topic) {
        if (kafkaTemplate == null) {
            log.error("Kafka broker not available");
        }
        Message<Transaction> message = MessageBuilder
                .withPayload(transaction)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }
}
