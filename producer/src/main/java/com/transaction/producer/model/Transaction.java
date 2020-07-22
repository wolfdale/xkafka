package com.transaction.producer.model;

/**
 * DTO representing Card Transaction.
 */
public class Transaction {
    String uuid;
    String status;
    String client;

    public Transaction(String uuid, String status, String client){
        this.uuid = uuid;
        this.status = status;
        this.client = client;
    }

    @Override
    public String toString(){
        return "[" + this.uuid + ", " + this.status + ", " + this.client + "]";
    }
}
