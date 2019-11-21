package com.transaction.producer.model;

/**
 * POJO for Card Transaction.
 */
public class Transaction {
    String uuid;
    String status;

    public Transaction(String uuid, String status){
        this.uuid = uuid;
        this.status = status;
    }

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

    @Override
    public String toString(){
        return "[" + this.uuid + ", " + this.status + "]";
    }
}
