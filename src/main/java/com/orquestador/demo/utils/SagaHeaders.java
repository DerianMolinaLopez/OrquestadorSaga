package com.orquestador.demo.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data

public class SagaHeaders {
    private String component;
    private String correlationId;
    private String stepId;
    private String objetivo;

    public Map<String, Object> toMap() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("component", component);
        headers.put("correlationId", correlationId);
        headers.put("stepId", stepId);
        headers.put("objetivo", objetivo);
        return headers;
    }

    public Map<String,Object> toMapErrors(  ){
        Map<String, Object> headers = new HashMap<>();
        headers.put("component", component);
        headers.put("correlationId", correlationId);
        headers.put("stepId", stepId);
        headers.put("objetivo", objetivo);

        return headers;
    }

 
    
}
