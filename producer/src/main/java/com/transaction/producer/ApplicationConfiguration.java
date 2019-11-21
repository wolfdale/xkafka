package com.transaction.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value(value = "transaction.generation.rate")
    public String TRANSACTION_GENERATION_RATE;

}
