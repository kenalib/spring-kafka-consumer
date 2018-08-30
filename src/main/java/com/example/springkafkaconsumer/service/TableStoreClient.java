package com.example.springkafkaconsumer.service;

import com.alicloud.openservices.tablestore.ClientConfiguration;
import com.alicloud.openservices.tablestore.model.AlwaysRetryStrategy;
import com.alicloud.openservices.tablestore.model.BatchWriteRowRequest;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.RowChange;

import java.util.ResourceBundle;

abstract class TableStoreClient {
    String endPoint;
    String accessKeyId;
    String accessKeySecret;
    String instanceName;
    ClientConfiguration clientConfiguration;

    void readAccountInfo() {
        ResourceBundle res = ResourceBundle.getBundle("account");

        endPoint = res.getString("account.endPoint");
        accessKeyId = res.getString("account.accessKeyId");
        accessKeySecret = res.getString("account.accessKeySecret");
        instanceName = res.getString("account.instanceName");
    }

    void createClientConfiguration() {
        clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeoutInMillisecond(5000);
        clientConfiguration.setSocketTimeoutInMillisecond(5000);
        clientConfiguration.setRetryStrategy(new AlwaysRetryStrategy());
    }

    abstract void runBatchWriteRowRequest(BatchWriteRowRequest batchWriteRowRequest);
    abstract void putRequested(String tableName, RowChange rowChange);
    abstract void removeRequested(String tableName, PrimaryKey primaryKey);
    abstract int getCurrentRequestCount();
    abstract void shutdown();
}
