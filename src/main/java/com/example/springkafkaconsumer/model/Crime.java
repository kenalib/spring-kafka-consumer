package com.example.springkafkaconsumer.model;

public class Crime {

    private Schema schema;
    private CrimePayload payload;

    public Crime() {
    }

    public Crime(Schema schema, CrimePayload payload) {
        this.schema = schema;
        this.payload = payload;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public CrimePayload getPayload() {
        return payload;
    }

    public void setPayload(CrimePayload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Crime: " + schema.toString() + " " + payload.toString();
    }
}
