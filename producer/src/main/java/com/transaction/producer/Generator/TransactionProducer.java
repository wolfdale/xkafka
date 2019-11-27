package com.transaction.producer.Generator;

import com.transaction.producer.dispatcher.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class generate the transaction data.
 * Could be extended to send data to redis server.
 */
public class TransactionProducer {
    private static Logger log = LoggerFactory.getLogger(TransactionProducer.class);
    private ScheduledExecutorService executorService;

    public TransactionProducer() {
        log.info("Starting to generate transaction ...");
        executorService = Executors.newScheduledThreadPool(1);
        init();
    }

    public void init() {
        executorService.scheduleAtFixedRate(new Dispatch(), 1,
                1000,
                TimeUnit.MILLISECONDS);
    }

}
