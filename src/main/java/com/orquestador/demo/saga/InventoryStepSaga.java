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
import com.orquestador.demo.interfaces.SagaStepLogRepository;

@Component
@Order(1)
public class InventoryStepSaga implements SagaStep{
      private static final Logger logger = LoggerFactory.getLogger(InventoryStepSaga.class);
      @Autowired
      private SagaStepLogRepository sagaStepLogRepository;
      @Autowired
      private ControllerKafkaPublisher kafkaPublisher;

      private final String stepName = "InventoryStepSaga";
      private final String topic = "inventarios";

  

     @Override
    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException{
        logger.info("Ejecutando InventoryStepSaga");
         JsonNode payloadToInventory = ctx.getPayload().get("cart");
         ObjectNode inventoryNode = (ObjectNode) payloadToInventory;
         inventoryNode.put("correlationId", ctx.getCorrelationId());
         logger.info("Payload enviado a inventario: {}", inventoryNode.toString());
         this.kafkaPublisher.publish(inventoryNode.toString(), topic);
        

    }
    @Override
    public void compensate() throws SagaStepCompensateException{
        logger.info("Compensando InventoryStepSaga");

    }
    @Override
    public String getStepName() {
        return stepName;
    }

    
 }


