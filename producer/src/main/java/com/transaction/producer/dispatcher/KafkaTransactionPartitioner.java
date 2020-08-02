package com.transaction.producer.dispatcher;

import com.transaction.producer.model.Transaction;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * A custom partitioner to make sure all [Rejected] transaction
 * to fall on specific Kafka partition rather than evenly distributed
 * to all partitions in the cluster.
 *
 */
@Component
public class KafkaTransactionPartitioner implements Partitioner {
    private static Logger log = LoggerFactory.getLogger(KafkaTransactionPartitioner.class);

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int numberOfPartitions = partitionInfos.size();

        if (numberOfPartitions > 1 && ((Transaction) value).getStatus().equalsIgnoreCase("Rejected")) {
            return 0;
        } else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numberOfPartitions;
        }
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
