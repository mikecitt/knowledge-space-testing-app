package com.service.kspace.service;

import net.minidev.json.JSONObject;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SparqlService {
    public String importData() {
        ResponseEntity<Map> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/api/export", Map.class);
        UpdateRequest request;
        UpdateProcessor qe;
        String queryString;

        queryString =
                "PREFIX base: <http://ftn.uns.ac.rs/> "
                        + "PREFIX student: <http://ftn.uns.ac.rs/student/> "
                        + "PREFIX domain: <http://ftn.uns.ac.rs/domain/> "
                        + "PREFIX domainproblem: <http://ftn.uns.ac.rs/domain-problem/> "
                        + "PREFIX test: <http://ftn.uns.ac.rs/test/> "
                        + "PREFIX section: <http://ftn.uns.ac.rs/section/> "
                        + "PREFIX item: <http://ftn.uns.ac.rs/item/> "
                        + "PREFIX answer: <http://ftn.uns.ac.rs/answer/> "
                        + "PREFIX takentest: <http://ftn.uns.ac.rs/taken-test/> "
                        + "PREFIX knowledgespace: <http://ftn.uns.ac.rs/knowledge-space/> "
                        + "INSERT DATA{";
        List<?> entities = (List<?>) responseEntity.getBody().get("STUDENT");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            Date date = new Date();
            date.setTime((long)entity.get("DATE_OF_BIRTH"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            queryString
                    +="student:" + entity.get("ID") + " "
                    + "base:name \"" + entity.get("FIRST_NAME") + " " + entity.get("LAST_NAME") + "\" ; "
                    + "base:born_on \"" + sdf.format(date) + "\" ; "
                    + "base:email \"" + entity.get("EMAIL") + "\" . ";
        }

        entities = (List<?>) responseEntity.getBody().get("DOMAIN");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="domain:" + entity.get("ID") + " "
                    + "base:name \"" + entity.get("NAME")  + "\" . ";
        }

        entities = (List<?>) responseEntity.getBody().get("DOMAIN_PROBLEM");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="domainproblem:" + entity.get("ID") + " "
                    + "base:name \"" + entity.get("NAME")  + "\" . ";
        }

        entities = (List<?>) responseEntity.getBody().get("KNOWLEDGE_SPACE");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="knowledgespace:" + entity.get("ID") + " "
                    + "base:name \"" + entity.get("NAME")  + "\" ; "
                    + "base:is_real \"" + entity.get("IS_REAL") + "\" ; "
                    + "base:part_of " + "domain:" + entity.get("DOMAIN_ID") + " . "
                    + "domain:" + entity.get("DOMAIN_ID") + " "
                    + "base:has_part knowledgespace:" + entity.get("ID") + " . ";
        }

        entities = (List<?>) responseEntity.getBody().get("EDGE");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="domainproblem:" + entity.get("FROM_ID") +" "
                    + "knowledgespace:" + entity.get("KNOWLEDGE_SPACE_ID") + " domainproblem:" + entity.get("TO_ID")  + " . ";
        }


        entities = (List<?>) responseEntity.getBody().get("TEST");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            Date date = new Date();
            Date date2 = new Date();
            date.setTime((long)entity.get("VALID_FROM"));
            date2.setTime((long)entity.get("VALID_UNTIL"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            queryString
                    +="test:" + entity.get("ID") +" "
                    + "base:name \"" + entity.get("NAME")  + "\" ; "
                    + "base:length \"" + entity.get("TIMER")  + "\" ; "
                    + "base:valid_from \"" + sdf.format(date) + "\" ; "
                    + "base:valid_until \"" + sdf.format(date2) + "\" ; "
                    + "base:belong domain:" + entity.get("DOMAIN_ID") + " . ";
        }

        entities = (List<?>) responseEntity.getBody().get("SECTION");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="section:" + entity.get("ID") +" "
                    + "base:part_of test:" + entity.get("TEST_ID") + " ; "
                    + "base:name \"" + entity.get("NAME")  + "\" . "
                    + "test:" + entity.get("TEST_ID") + " "
                    + "base:has_part section:" + entity.get("ID") + " . ";
        }

        entities = (List<?>) responseEntity.getBody().get("ITEM");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="item:" + entity.get("ID") +" "
                    + "base:part_of section:" + entity.get("SECTION_ID")  + " ; "
                    + "base:belong domainproblem:" + entity.get("DOMAIN_PROBLEM_ID")  + " ; "
                    + "base:text \"" + entity.get("TEXT")  + "\" . ";
        }

        entities = (List<?>) responseEntity.getBody().get("ANSWER");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="answer:" + entity.get("ID") +" "
                    + "base:part_of item:" + entity.get("ITEM_ID")  + " ; "
                    + "base:scores \"" + entity.get("POINTS")  + "\" ; "
                    + "base:text \"" + entity.get("TEXT")  + "\" . ";
        }

        entities = (List<?>) responseEntity.getBody().get("TAKEN_TEST");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            Date date = new Date();
            Date date2 = new Date();
            date.setTime((long)entity.get("START"));
            date2.setTime((long)entity.get("END"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            queryString
                    +="takentest:" + entity.get("ID") +" "
                    + "base:start \"" + sdf.format(date) + "\" ; "
                    + "base:end \"" + sdf.format(date2)  + "\" ; "
                    + "base:from test:" + entity.get("TEST_ID")  + " . "
                    + "student:" + entity.get("TAKEN_BY_ID") + " "
                    + "base:had takentest:" + entity.get("ID") + " . ";
        }

        entities = (List<?>) responseEntity.getBody().get("TAKEN_TEST_ANSWERS");
        for(int i = 0; i < entities.size(); i++) {
            LinkedHashMap<String, ?> entity = (LinkedHashMap<String, ?>) entities.get(i);
            queryString
                    +="takentest:" + entity.get("TAKEN_TEST_ID") +" "
                    + "base:answered answer:" + entity.get("ANSWERS_ID")  + " . ";
        }

        queryString += "} ;";
        request = UpdateFactory.create(queryString);
        qe = UpdateExecutionFactory.createRemote(request,
                "http://localhost:3030/knowledge/update");
        qe.execute();

        return queryString;
    }
}
