package com.transaction.consumer.listener;

import com.google.gson.Gson;
import com.transaction.consumer.EsConfiguration;
import com.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaConsumerListener {
    private static Logger log = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    EsConfiguration elasticConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    ConcurrentKafkaListenerContainerFactory<String, String> consumer;

    @KafkaListener(topics =  "${kafka.topic.name}", groupId =  "${kafka.group.id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumeJson(String transaction) {
        log.info("Consumed Message: " + transaction);
        Gson gson = new Gson();
        Transaction trans = gson.fromJson(transaction, Transaction.class);
        if(elasticConfig.isElasticSearchEnabled()) {
            IndexCoordinates indexCoordinates =
                    elasticsearchOperations.getIndexCoordinatesFor(Transaction.class);
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(trans.getUuid())
                    .withObject(trans)
                    .build();

            String docId = elasticsearchOperations.index(indexQuery, indexCoordinates);
            log.info("Document Id :" + docId);
        }

    }

}
