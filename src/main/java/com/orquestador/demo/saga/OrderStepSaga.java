package com.orquestador.demo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.orquestador.demo.controller.ControllerKafkaPublisher;
import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

@Component
@Order(2)
public class OrderStepSaga implements  SagaStep{
  @Autowired
   private ControllerKafkaPublisher kafkaPublisher;
   private static final Logger logger = LoggerFactory.getLogger(OrderStepSaga.class);
   private final String stepName = "OrderStepSaga";
   private final String topic = "ordenes";
  
    @Override
    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException{
        logger.info("Ejecutando OrderStepSaga");
        JsonNode payloadToInventory = ctx.getPayload().get("cart");
        ObjectNode inventoryNode = (ObjectNode) payloadToInventory;
        inventoryNode.put("correlationId", ctx.getCorrelationId());
        logger.info("Payload enviado a inventario: {}", inventoryNode.toString());
        this.kafkaPublisher.publish(inventoryNode.toString(), topic);
    }
    @Override
    public void compensate() throws SagaStepCompensateException{
        logger.info("Compensando OrderStepSaga");

    }
    @Override
    public String getStepName() {
        return stepName;
    }
    
}
