package com.service.kspace.service;

import com.service.kspace.dto.Result;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.HttpQuery;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SparqlService {
    public String importData() {
        ResponseEntity<Map> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/api/export", Map.class);
        UpdateRequest request;
        UpdateProcessor qe;
        String queryString;
        Random random = new Random();

        queryString =
                "SELECT ?subject ?predicate ?object " +
                        "WHERE { " +
                        "  ?subject ?predicate <http://purl.org/vocab/aiiso/schema#Course>. " +
                        "}";
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/knowledge/query",
                queryString
        );
        ResultSet rs = queryExecution.execSelect();
        List<RDFNode> courses = new ArrayList<>();
        Collections.shuffle(courses);
        while(rs.hasNext()) {
            QuerySolution qs = rs.nextSolution();
            if (qs.get("subject").toString()
                    .startsWith("http://www.knowledge-space-testing.com")) {
                courses.add(qs.get("subject"));
            }
        }
        Collections.shuffle(courses);

        queryString =
                "PREFIX base: <http://www.knowledge-space-testing.com/> "
                        + "PREFIX student: <http://www.knowledge-space-testing.com/student/> "
                        + "PREFIX domain: <http://www.knowledge-space-testing.com/domain/> "
                        + "PREFIX domainproblem: <http://www.knowledge-space-testing.com/domain-problem/> "
                        + "PREFIX test: <http://www.knowledge-space-testing.com/test/> "
                        + "PREFIX section: <http://www.knowledge-space-testing.com/section/> "
                        + "PREFIX item: <http://www.knowledge-space-testing.com/item/> "
                        + "PREFIX answer: <http://www.knowledge-space-testing.com/answer/> "
                        + "PREFIX takentest: <http://www.knowledge-space-testing.com/taken-test/> "
                        + "PREFIX knowledgespace: <http://www.knowledge-space-testing.com/knowledge-space/> "
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
                    + "base:belong domain:" + entity.get("DOMAIN_ID") + " ; "
                    + "base:part_of <" + courses.get(random.nextInt(courses.size())) + "> . ";
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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

    public Result selectFromJena(Integer queryId) throws Exception {
        String queryString = "query";

        InputStream is = getClass().getClassLoader().getResourceAsStream(queryString + queryId + ".txt");

        if (is == null) {
            throw new Exception("There is no known query.");
        }

        StringBuilder query = new StringBuilder();
        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                query.append(line + " ");
            }

        }
        queryString = query.toString();
        QueryExecution queryExecution = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/knowledge/query",
                queryString
        );
        ResultSet rs = queryExecution.execSelect();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ResultSetFormatter.outputAsJSON(outputStream, rs);
        String stringToParse = outputStream.toString();

        JSONParser jsonParser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jsonObject = (JSONObject) jsonParser.parse(stringToParse);
        List<JSONObject> entities = (List<JSONObject>) ((JSONObject)jsonObject.get("results")).get("bindings");
        List<HashMap<String, String>> finalEntities = new ArrayList<>();
        Result res = new Result();
        if (entities.size() > 0) {
            res.setColumns(entities.get(0).keySet().toArray(new String[0]));
            entities.forEach(x -> {
                HashMap<String, String> entity = new HashMap<>();
                for(String key : x.keySet()) {
                    entity.put(key, ((JSONObject)x.get(key)).get("value").toString());
                }
                finalEntities.add(entity);
            });
            res.setData(finalEntities);
        }

        queryExecution.close();
        return res;
    }
}
