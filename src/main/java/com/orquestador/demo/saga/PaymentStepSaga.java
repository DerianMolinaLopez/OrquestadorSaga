package com.orquestador.demo.saga;

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
@Order(3)
public class PaymentStepSaga implements SagaStep{
    @Autowired
    private ControllerKafkaPublisher kafkaPublisher;
       private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PaymentStepSaga.class);
    private final String stepName = "PaymentStepSaga";
    private final String topic = "pagos";

   @Override
    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException{
        logger.info("Ejecutando PaymentStepSaga");
        String payloadModified = ModifyPayloadJson.addCorrelationIdToObjectNode(ctx.getPayload().get("payment"), ctx.getCorrelationId());
        logger.info("Payload enviado a inventario: {}", payloadModified);
        this.kafkaPublisher.publish(payloadModified, topic);

    }
    @Override
    public void compensate(HandleComponentErrors errorContext) throws SagaStepCompensateException{
        logger.info("Compensando PaymentStepSaga");
             String message = this.convertErrorstoString(errorContext);
        this.kafkaPublisher.publish(message, topic);

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
