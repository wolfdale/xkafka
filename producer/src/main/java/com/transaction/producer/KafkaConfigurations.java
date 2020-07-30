package com.transaction.producer;

import com.transaction.producer.model.Transaction;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaConfigurations {
    private static Logger log = LoggerFactory.getLogger(KafkaConfigurations.class);

    @Value(value = "${kafka.bootstrap.server}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic.name}")
    public String topic;

    @Value(value = "${producer.client.type}")
    public String client;

    @Value(value = "${kafka.metadata.request}")
    public String metadataRequestInterval;


    private ProducerFactory<String, Transaction> producerFactory() {
        log.info("Kafka broker address {} ", bootstrapAddress);
        HashMap<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProperties);
    }

    @Bean
    public KafkaTemplate<String, Transaction> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(), true);
    }

    public String getKafkaTopic() {
        return this.topic;
    }

    public String getClient() {
        return this.client;
    }

    public String getBootstrapServer() {
        return this.bootstrapAddress;
    }

    public String getMetadataRequestInterval() {
        return this.metadataRequestInterval;
    }

}
