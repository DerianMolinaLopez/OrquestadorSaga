package com.orquestador.demo.saga;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orquestador.demo.controller.ControllerKafkaPublisher;
import com.orquestador.demo.dto.JsonNodeModified;
import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;
import com.orquestador.demo.utils.ModifyPayloadJson;
import com.orquestador.demo.utils.SagaHeaders;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;
@Component
@Order(1)
public class InventoryStepSaga implements SagaStep{
      private static final Logger logger = LoggerFactory.getLogger(InventoryStepSaga.class);
      private final ObjectMapper mapper = new ObjectMapper();

      @Autowired
      private ControllerKafkaPublisher kafkaPublisher;
      private final String stepName = "InventoryStepSaga";
      private final String topic = "inventarios";
      private SagaHeaders sagaHeaders;

      public InventoryStepSaga(){
        this.sagaHeaders = new SagaHeaders();
 
      }
    


    @Override
    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException {
        logger.info("Ejecutando InventoryStepSaga");
        

        JsonNode cartNode = ctx.getPayload().get("cart");
        ObjectNode cartObjectNode = ModifyPayloadJson.convertJsonNodeToObjectNode(cartNode);
        

        JsonNodeModified<String> jsonModified = new JsonNodeModified<>();
        jsonModified.setKey("correlationId");
        jsonModified.setValue(ctx.getCorrelationId());
        jsonModified.setObjectNode(cartObjectNode);
        

        JsonNode payloadPrepared = ModifyPayloadJson.addToJsonNode(jsonModified);

        String payloadModified = payloadPrepared.toString();
        logger.info("Payload enviado a inventario: {}", payloadModified);
        
        this.sagaHeaders.setComponent(stepName);
        this.sagaHeaders.setCorrelationId(ctx.getCorrelationId());
        this.sagaHeaders.setObjetivo("grabado");
        this.sagaHeaders.setStepId(ctx.getStepId());
        Map<String, Object> headers = this.sagaHeaders.toMap();
        
        this.kafkaPublisher.publishWithHeaders(payloadModified, topic, headers);
    }


    @Override
    public void compensate(HandleComponentErrors errorContext) throws SagaStepCompensateException{
        logger.info("Compensando InventoryStepSaga");
  
        Map<String,Object> headers = new HashMap<>();
         headers.put("component", "inventarios");
         headers.put("correlationId", errorContext.getNumberOfOperation());
         headers.put("objetivo","compensacion");
        this.kafkaPublisher.publishWithHeaders(errorContext.getErrorMessage(), topic, headers);
  

    }


    @Override
    public String getStepName() {
        return stepName;
    }

    
 }


