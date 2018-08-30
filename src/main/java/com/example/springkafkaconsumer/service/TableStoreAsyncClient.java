package com.example.springkafkaconsumer.service;

import com.alicloud.openservices.tablestore.AsyncClient;
import com.alicloud.openservices.tablestore.TableStoreCallback;
import com.alicloud.openservices.tablestore.model.BatchWriteRowRequest;
import com.alicloud.openservices.tablestore.model.BatchWriteRowResponse;
import com.alicloud.openservices.tablestore.model.PrimaryKey;
import com.alicloud.openservices.tablestore.model.RowChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class TableStoreAsyncClient extends TableStoreClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableStoreAsyncClient.class);

    private TableStoreCallback<BatchWriteRowRequest, BatchWriteRowResponse> callback;
    private AsyncClient client;
    private static ConcurrentMap<String, RowChange> requested = new ConcurrentHashMap<>();

    TableStoreAsyncClient(TableStoreAsyncCallback callback) {
        this.callback = callback;
        callback.setAsyncClient(this);

        readAccountInfo();
        createClientConfiguration();

        client = new AsyncClient(endPoint, accessKeyId, accessKeySecret, instanceName, clientConfiguration);
    }

    void shutdown() {
        LOGGER.info("client shutdown...");
        client.shutdown();
    }

    void putRequested(String tableName, RowChange rowChange) {
        requested.put(tableName + rowChange.getPrimaryKey().toString(), rowChange);
    }

    void removeRequested(String tableName, PrimaryKey primary) {
        requested.remove(tableName + primary.toString());
    }

    int getCurrentRequestCount() {
        return requested.size();
    }

    void runBatchWriteRowRequest(final BatchWriteRowRequest batchWriteRowRequest) {
        LOGGER.info("Running Batch: " + batchWriteRowRequest.getRowsCount());

        client.batchWriteRow(batchWriteRowRequest, callback);

        Map<String, List<RowChange>> tableRowChange = batchWriteRowRequest.getRowChange();

        tableRowChange.forEach((tableName, rowChanges)->
                rowChanges.forEach(rowChange ->
                        putRequested(tableName, rowChange)));

        LOGGER.info(tableRowChange.toString());
    }
}
