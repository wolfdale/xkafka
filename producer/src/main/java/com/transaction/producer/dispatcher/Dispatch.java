package com.transaction.producer.dispatcher;

import com.fasterxml.uuid.Generators;
import com.transaction.producer.model.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Dispatch implements Runnable {
    private static Logger log = LoggerFactory.getLogger(Dispatch.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Environment env;

    public Dispatch() {
        /*HashMap<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProperties),
            true);*/
    }

    public void run() {
        String isApproved = Math.random() < 0.5 ? "Approved" : "Rejected";
        Transaction transaction = new Transaction(Generators.timeBasedGenerator().generate().toString(),
                isApproved);
        log.info("Transaction Generated {}", transaction.toString());
        dispatchPayloadToKafka(transaction.toString());
    }

    public void dispatchPayloadToKafka(String payload) {
        if (kafkaTemplate == null) {
            log.error("KafkaTemp not available");
        }
        kafkaTemplate.send("transactions", payload);
    }
}
