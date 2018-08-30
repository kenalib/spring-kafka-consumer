package com.example.springkafkaconsumer.service;

import com.alicloud.openservices.tablestore.model.ColumnValue;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.PrimaryKeyBuilder;
import com.alicloud.openservices.tablestore.model.PrimaryKeyValue;

class TableStoreUtil {
    static PrimaryKey buildPrimaryKey(String pkName, String pkType, String pkValue) {
        switch (pkType) {
            case "string":
                return buildPrimaryKey(pkName, pkValue);
            case "integer":
                int tmp = Integer.parseInt(pkValue);
                return buildPrimaryKey(pkName, tmp);
            default:
                throw new RuntimeException("Type not found");
        }
    }

    private static PrimaryKey buildPrimaryKey(String pkName, long pkValue) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(pkName, PrimaryKeyValue.fromLong(pkValue));
        return primaryKeyBuilder.build();
    }

    private static PrimaryKey buildPrimaryKey(String pkName, String pkValue) {
        PrimaryKeyBuilder primaryKeyBuilder = PrimaryKeyBuilder.createPrimaryKeyBuilder();
        primaryKeyBuilder.addPrimaryKeyColumn(pkName, PrimaryKeyValue.fromString(pkValue));
        return primaryKeyBuilder.build();
    }

    static ColumnValue genColValue(String col, String colType) {
        ColumnValue colValue;

        switch (colType) {
            case "string":
                colValue = ColumnValue.fromString(col);
                break;
            case "integer": {
                if (col.equals("")) col = "0";
                int tmp = Integer.parseInt(col);
                colValue = ColumnValue.fromLong(tmp);
                break;
            }
            case "boolean": {
                boolean tmp = Boolean.parseBoolean(col);
                colValue = ColumnValue.fromBoolean(tmp);
                break;
            }
            case "double": {
                if (col.equals("")) col = "0";
                double tmp = Double.parseDouble(col);
                colValue = ColumnValue.fromDouble(tmp);
                break;
            }
            default:
                throw new RuntimeException("type not found");
        }

        return colValue;
    }
}
