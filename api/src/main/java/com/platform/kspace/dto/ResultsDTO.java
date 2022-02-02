package com.platform.kspace.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResultsDTO {
    private Map<UUID, Byte[]> results;

    public ResultsDTO() {
        results = new HashMap<>();
    }

    public ResultsDTO(Map<UUID, Byte[]> results) {
        this.results = results;
    }

    public Map<UUID, Byte[]> getResults() {
        return results;
    }

    public void setResults(Map<UUID, Byte[]> results) {
        this.results = results;
    }
}
