package com.orquestador.demo.saga;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AplicationSagaContext {
    private String correlationId;
    private JsonNode payload;

}
