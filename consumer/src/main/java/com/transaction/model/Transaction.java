package com.transaction.model;

public class Transaction {
    String uuid;
    String status;
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

    public Transaction(String uuid, String status, String client){
        this.uuid = uuid;
        this.status = status;
        this.client = client;
    }
    public Transaction(){

    }

    @Override
    public String toString(){
        return "[" + this.uuid + ", " + this.status + ", " + this.client + "]";
    }
}
