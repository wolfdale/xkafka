package com.transaction.producer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO representing Card Transaction.
 */
public class Transaction implements Serializable {
    private static final long serialVersionUID = 123L;
    @JsonProperty("uuid")
    String uuid;
    @JsonProperty("status")
    String status;
    @JsonProperty("client")
    String client;

    public Transaction(String uuid, String status, String client) {
        this.uuid = uuid;
        this.status = status;
        this.client = client;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Transaction() {
    }

    @Override
    public String toString() {
        return "[" + this.uuid + ", " + this.status + ", " + this.client + "]";
    }
}
