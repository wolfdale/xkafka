package com.transaction.consumer.listener;

import com.transaction.consumer.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaConsumerListener {
    private static Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, String> consumer;

    @KafkaListener(topics =  "${kafka.topic.name}", groupId =  "${kafka.group.id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeJson(String transaction) {
        log.info("Consumed Message: " + transaction);
    }

}
