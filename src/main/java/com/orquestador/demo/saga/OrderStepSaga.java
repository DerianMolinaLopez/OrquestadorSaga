package com.orquestador.demo.saga;

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
@Order(2)
public class OrderStepSaga implements  SagaStep{
  @Autowired
   private ControllerKafkaPublisher kafkaPublisher;
   private final ObjectMapper mapper = new ObjectMapper();
   private static final Logger logger = LoggerFactory.getLogger(OrderStepSaga.class);
   private final String stepName = "OrderStepSaga";
   private final String topic = "ordenes";
   private SagaHeaders sagaHeaders;
  

   public OrderStepSaga(){
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
        System.out.println("identificando los headers en orders");
        System.out.println(headers.toString());
        
        this.kafkaPublisher.publishWithHeaders(payloadModified, topic, headers);
    }
    @Override
    public void compensate(HandleComponentErrors errorContext) throws SagaStepCompensateException{
        logger.info("Compensando OrderStepSaga");

  //      this.kafkaPublisher.publishWithHeaders(stepName, topic, headers);
      //  String message = this.convertErrorstoString(errorContext);
      //  this.kafkaPublisher.publish(message, topic);

    }

    @Override
    public String getStepName() {
        return stepName;
    }
    
}
