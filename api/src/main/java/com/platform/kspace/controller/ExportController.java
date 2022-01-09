package com.platform.kspace.controller;

import com.platform.kspace.service.ExportService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("api/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping
    public ResponseEntity<?> exportDatabaseDataToJson() {
        try {
            return ResponseEntity.ok(exportService.exportDatabaseDataToJson());
        } catch (SQLException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
