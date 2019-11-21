package com.transaction.producer;

import com.transaction.producer.Generator.TransactionProducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        new TransactionProducer();
    }

}
