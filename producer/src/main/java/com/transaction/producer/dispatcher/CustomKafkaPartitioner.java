package com.transaction.producer.dispatcher;

import com.transaction.producer.model.Transaction;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * A custom partitioner to allow [Rejected] transaction
 * to fall on specific Kafka partition.
 */
@Component
public class CustomKafkaPartitioner implements Partitioner {
    private static Logger log = LoggerFactory.getLogger(CustomKafkaPartitioner.class);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numberOfPartitions = partitionInfos.size();

        log.info("Number of Partitions : " + numberOfPartitions);
        log.info("Key : " + key);
        log.info("Value : " + value);
        Transaction t = (Transaction) value;

        if (numberOfPartitions > 1 && t.getStatus().equalsIgnoreCase("Rejected")) {
            log.info("Sending this to zero partition");
            return 0;
        }
        // Need to hash key and divide it with number of remaining partitions.
        return 1;
    }

    @Override
    public void close() {

    }

    @Override
    public void onNewBatch(String topic, Cluster cluster, int prevPartition) {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
