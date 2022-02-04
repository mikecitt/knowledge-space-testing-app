package com.service.kspace.controller;

import com.service.kspace.service.SparqlService;
import net.minidev.json.parser.ParseException;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("sparql")
public class SparqlController {

    @Autowired
    private SparqlService sparqlService;

    @GetMapping("import")
    public ResponseEntity<?> importData() {
        return ResponseEntity.ok(sparqlService.importData());
    }

    @GetMapping("view")
    public ResponseEntity<?> viewData() {
        ResponseEntity<Map> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/api/export", Map.class);
        return responseEntity;
    }

    @GetMapping("query")
    public ResponseEntity<?> selectData(@RequestParam Integer id) {
        try {
            return ResponseEntity.ok(sparqlService.selectFromJena(id));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
