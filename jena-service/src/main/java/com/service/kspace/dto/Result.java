package com.service.kspace.dto;

import java.util.HashMap;
import java.util.List;

public class Result {
    private String[] columns;
    private List<HashMap<String, String>> data;

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public List<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, String>>  data) {
        this.data = data;
    }
}
