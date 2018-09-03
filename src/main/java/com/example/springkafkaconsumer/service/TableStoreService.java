package com.example.springkafkaconsumer.service;

import com.alicloud.openservices.tablestore.model.*;
import com.example.springkafkaconsumer.model.Crime;
import com.example.springkafkaconsumer.model.SchemaField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Service
public class TableStoreService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableStoreService.class);

    private String tableName;
    private String primaryName;
    private String primaryType;

    private static ResourceBundle type;
    private static TableStoreAsyncCallback callback = new TableStoreAsyncCallback();
    private static TableStoreClient asyncClient = new TableStoreAsyncClient(callback);

    public TableStoreService() {
        tableName = "ots_chicago_crime";
        primaryName = "ID";
        primaryType = "integer";

        type = ResourceBundle.getBundle(tableName);
    }

    public void save(Crime crime) {
        LOGGER.info("saving " + crime.getPayload().get(primaryName));

        BatchWriteRowRequest batchWriteRowRequest = new BatchWriteRowRequest();

        addRowChange(batchWriteRowRequest, crime);

        asyncClient.runBatchWriteRowRequest(batchWriteRowRequest);
    }

    public void saveMulti(List<Crime> crimes) {
        BatchWriteRowRequest batchWriteRowRequest = new BatchWriteRowRequest();

        crimes.forEach(crime -> addRowChange(batchWriteRowRequest, crime));

        asyncClient.runBatchWriteRowRequest(batchWriteRowRequest);
    }

    private void addRowChange(BatchWriteRowRequest batchWriteRowRequest, Crime crime) {
        // exclude invalid data
        if (crime.getPayload().get(primaryName) == null) return;

        try {
            RowPutChange rowPutChange = genRowPutChange(crime);
            batchWriteRowRequest.addRowChange(rowPutChange);
        } catch (Exception e) {
            LOGGER.error("PrimaryKey: " + crime.getPayload().get(primaryName));
            LOGGER.error("Payload: " + crime.getPayload());
            e.printStackTrace();
        }
    }

    private RowPutChange genRowPutChange(Crime crime) {
        Map<String, String> payload = crime.getPayload();

        PrimaryKey primaryKey = TableStoreUtil.buildPrimaryKey(
                primaryName, primaryType, payload.get(primaryName)
        );

        RowPutChange rowPutChange = new RowPutChange(tableName, primaryKey);
        List<SchemaField> fields = crime.getSchema().getFields();

        for (SchemaField field : fields) {
            String colName = field.getField();
            if (colName.equals(primaryName)) continue;

            String colNameFix = colName.replace(" ", "_");

            String colType = type.getString(colNameFix);
            String colData = payload.get(colName);

            ColumnValue colValue = TableStoreUtil.genColValue(colData, colType);
            rowPutChange.addColumn(new Column(colNameFix, colValue));
        }

        return rowPutChange;
    }
}
