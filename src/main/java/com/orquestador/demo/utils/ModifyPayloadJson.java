package com.orquestador.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orquestador.demo.dto.JsonNodeModified;

public class ModifyPayloadJson {
    public static String addCorrelationIdToObjectNode(JsonNode  node, String correlationId) {
        ((ObjectNode) node) .put("correlationId", correlationId);
        return node.toString();
    }
   public static <T> JsonNode addToJsonNode(JsonNodeModified<T> jsonNodeWithNewFields) {
        ObjectNode node = jsonNodeWithNewFields.getObjectNode();
        String keyNode = jsonNodeWithNewFields.getKey();
        T value = jsonNodeWithNewFields.getValue();
        
        if (value instanceof JsonNode) {
            node.set(keyNode, (JsonNode) value);
        } else {
            node.put(keyNode, value.toString());
        }
        
    return node;
}
  
public static ObjectNode convertJsonNodeToObjectNode(JsonNode node) {
    ObjectNode objectNode = null;
    if(node.isObject()){
        objectNode = (ObjectNode) node;
    }
    return objectNode;
}
    

 
    
}
