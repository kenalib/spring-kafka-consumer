package com.example.springkafkaconsumer.model;

import java.util.List;

public class Schema {

    private String type;
    private List<SchemaField> fields;
    private boolean optional;
    private String name;

    public Schema() {
    }

    public Schema(String type, List<SchemaField> fields, boolean optional, String name) {
        this.type = type;
        this.fields = fields;
        this.optional = optional;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SchemaField> getFields() {
        return fields;
    }

    public void setFields(List<SchemaField> fields) {
        this.fields = fields;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
