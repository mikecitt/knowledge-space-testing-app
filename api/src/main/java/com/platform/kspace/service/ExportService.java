package com.platform.kspace.service;

import net.minidev.json.JSONObject;

import java.sql.SQLException;

public interface ExportService {
    JSONObject exportDatabaseDataToJson() throws SQLException;
}
