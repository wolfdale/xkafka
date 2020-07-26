package com.transaction.consumer.listener;

import com.transaction.consumer.EsConfiguration;
import com.transaction.consumer.model.EsTransactionPayload;
import com.transaction.consumer.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {
    private static Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    EsConfiguration elasticConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, Transaction> consumer;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeJson(@Payload Transaction transaction, @Headers MessageHeaders headers) {
        log.info("Message from kafka: " + transaction.toString());

        if (elasticConfig.isElasticSearchEnabled()) {
            try {
                IndexCoordinates indexCoordinates =
                        elasticsearchOperations.getIndexCoordinatesFor(EsTransactionPayload.class);
                IndexQuery indexQuery = new IndexQueryBuilder()
                        .withId(transaction.getUuid())
                        .withObject(buildPayload(transaction))
                        .build();

                String docId = elasticsearchOperations.index(indexQuery, indexCoordinates);
                log.info("Transaction stored in ES with Id :" + docId);
            } catch (Exception e) {
                log.error("Elastic Search is not available at this moment.");
            }
        }
    }

    private EsTransactionPayload buildPayload(Transaction t) {
        EsTransactionPayload esPayload = new EsTransactionPayload();
        esPayload.setClient(t.getClient());
        esPayload.setStatus(t.getStatus());
        esPayload.setUuid(t.getUuid());
        return esPayload;
    }
}
