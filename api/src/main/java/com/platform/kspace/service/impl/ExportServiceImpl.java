package com.platform.kspace.service.impl;

import com.platform.kspace.service.ExportService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private DataSource dataSource;

    @Override
    public JSONObject exportDatabaseDataToJson() throws SQLException {
        Connection connection = dataSource.getConnection();
        List<String> tables = loadTables(connection);
        JSONObject jsonObject = new JSONObject();

        for (String tableName : tables) {
            JSONArray array = new JSONArray();
            List<String> columns = loadColumns(connection, tableName);
            ResultSet dataSet = loadData(connection, tableName);
            while (dataSet.next()) {
                JSONObject record = new JSONObject();
                for (String column : columns) {
                    Object cellValue = dataSet.getObject(column);
                    if (cellValue instanceof byte[] && !column.equalsIgnoreCase("picture")) {
                        record.put(column, getUuidFromByteArray((byte[])dataSet.getObject(column)));
                    } else {
                        record.put(column, dataSet.getObject(column));
                    }
                }
                array.add(record);
            }
            jsonObject.put(tableName, array);
        }

        return jsonObject;
    }

    public String getUuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        UUID uuid = new UUID(high, low);
        return uuid.toString();
    }

    public List<String> loadTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'TABLE'");
        List<String> tablesName = new ArrayList<>();
        while(resultSet.next()) {
            tablesName.add(resultSet.getString("TABLE_NAME"));
        }
        return tablesName;
    }

    public List<String> loadColumns(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COLUMN_NAME FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE TABLE_NAME LIKE '" + tableName + "'");
        List<String> columnsName = new ArrayList<>();
        while(resultSet.next()) {
            columnsName.add(resultSet.getString("COLUMN_NAME"));
        }
        return columnsName;
    }

    public static ResultSet loadData(Connection connection, String tableName) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM " + tableName + "");
    }
}
