package com.service.kspace.service;

import net.minidev.json.JSONObject;

public interface SparqlService {
    JSONObject extractDataFromApi();
    void createOntologyBasedOnJson(JSONObject jsonObject);
    void uploadOntologyOnJena();
}
