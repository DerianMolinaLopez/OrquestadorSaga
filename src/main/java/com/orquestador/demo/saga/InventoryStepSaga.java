package com.orquestador.demo.saga;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orquestador.demo.controller.ControllerKafkaPublisher;
import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;
import com.orquestador.demo.utils.ModifyPayloadJson;
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

  

     @Override
    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException{
        logger.info("Ejecutando InventoryStepSaga");
        String payloadModified = ModifyPayloadJson.addCorrelationIdToObjectNode(ctx.getPayload().get("cart"), ctx.getCorrelationId());
                                            
         logger.info("Payload enviado a inventario: {}", payloadModified);

         Map<String, Object> headers = new HashMap<>();
          
         headers.put("component", "inventarios");
         headers.put("correlationId", ctx.getCorrelationId());
         headers.put("stepId",ctx.getStepId() );
         headers.put("objetivo","grabado");
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

   private String convertErrorstoString(HandleComponentErrors errorContext) {
    try {
        return this.mapper.writeValueAsString(errorContext);
    } catch (Exception e) {
        throw new RuntimeException("Error al convertir el objeto a String JSON", e);
    }
}

    @Override
    public String getStepName() {
        return stepName;
    }

    
 }


