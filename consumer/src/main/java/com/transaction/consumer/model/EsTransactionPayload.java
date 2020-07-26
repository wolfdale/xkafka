package com.transaction.consumer.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "transaction", createIndex = true, replicas = 1, shards = 1)
public class EsTransactionPayload {
    @Field(type = FieldType.Text)
    String uuid;

    @Field(type = FieldType.Text)
    String status;

    @Field(type = FieldType.Text)
    String client;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
