package com.transaction.consumer;

import com.transaction.consumer.listener.KafkaListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ConsumerApplication.class, args);
		KafkaListener.runConsumer();
	}

}
