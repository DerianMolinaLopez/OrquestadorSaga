package com.orquestador.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ModifyPayloadJson {
    public static String addCorrelationIdToObjectNode(JsonNode  node, String correlationId) {
        ((ObjectNode) node) .put("correlationId", correlationId);
        return node.toString();
    }
    
}
