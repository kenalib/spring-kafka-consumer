package com.example.springkafkaconsumer.model;

import java.util.Map;

public class Crime {

    private Schema schema;
    private Map<String, String> payload;

    public Crime() {
    }

    public Crime(Schema schema, Map<String, String> payload) {
        this.schema = schema;
        this.payload = payload;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Crime: " + schema.toString() + " " + payload.toString();
    }
}
