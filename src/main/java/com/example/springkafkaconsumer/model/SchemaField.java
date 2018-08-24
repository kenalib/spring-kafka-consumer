package com.example.springkafkaconsumer.model;

public class SchemaField {

    private String type;
    private String optional;
    private String field;

    public SchemaField() {
    }

    public SchemaField(String type, String optional, String field) {
        this.type = type;
        this.optional = optional;
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptional() {
        return optional;
    }

    public void setOptional(String optional) {
        this.optional = optional;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
