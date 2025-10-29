package com.orquestador.demo.dto;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.Data;

@Data
public class JsonNodeModified<T> {
    private String key;
    private T value;
    private ObjectNode objectNode;
    @Override
    public String toString() {
        return "JsonNodeModified [key=" + key + ", value=" + value + ", objectNode=" + objectNode.toString() + "]";
    }


    
}
