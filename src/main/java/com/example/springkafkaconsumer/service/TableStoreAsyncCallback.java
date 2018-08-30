package com.example.springkafkaconsumer.service;

import com.alicloud.openservices.tablestore.TableStoreCallback;
import com.alicloud.openservices.tablestore.model.BatchWriteRowRequest;
import com.alicloud.openservices.tablestore.model.BatchWriteRowResponse;
import com.alicloud.openservices.tablestore.model.RowChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

class TableStoreAsyncCallback implements TableStoreCallback<BatchWriteRowRequest, BatchWriteRowResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableStoreAsyncCallback.class);
    private TableStoreAsyncClient asyncClient;

    void setAsyncClient(TableStoreAsyncClient asyncClient) {
        this.asyncClient = asyncClient;
    }

    public void onCompleted(BatchWriteRowRequest req, BatchWriteRowResponse res) {
        Map<String, List<RowChange>> tableRowChange = req.getRowChange();

        tableRowChange.forEach((tableName, rowChanges)->
                rowChanges.forEach(rowChange ->
                        asyncClient.removeRequested(tableName, rowChange.getPrimaryKey())));

        if (!res.isAllSucceed()) {
            LOGGER.info("Partial Succeed " + res.getSucceedRows().size());

            for (BatchWriteRowResponse.RowResult row : res.getFailedRows()) {
                LOGGER.info("Failed rows:" + req.getRowChange(row.getTableName(), row.getIndex()).getPrimaryKey());
                LOGGER.info("Cause of failure:" + row.getError());
            }

            BatchWriteRowRequest retryRequest = req.createRequestForRetry(res.getFailedRows());
            LOGGER.info("retryRequest count: " + retryRequest.getRowsCount());

            asyncClient.runBatchWriteRowRequest(retryRequest);
        }

        checkCurrentRequestsSize();
    }

    public void onFailed(BatchWriteRowRequest req, Exception e) {
        LOGGER.error("Resubmitting onFailed " + e.getMessage());
        asyncClient.runBatchWriteRowRequest(req);

        checkCurrentRequestsSize();
    }

    private void checkCurrentRequestsSize() {
        int n = asyncClient.getCurrentRequestCount();
        LOGGER.info("remain: " + n);
    }
}
