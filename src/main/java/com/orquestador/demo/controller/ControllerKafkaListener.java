package com.orquestador.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orquestador.demo.services.SagaInstanceService;
import com.orquestador.demo.services.StepLogSagaService;
import com.orquestador.demo.services.WorkSagaService;
import static com.orquestador.demo.utils.Constants.GroupIdStringConstants.GROUP_ID_CONFIRMACIONES;
import static com.orquestador.demo.utils.Constants.GroupIdStringConstants.GROUP_ID_ERRORES;
import static com.orquestador.demo.utils.Constants.GroupIdStringConstants.GROUP_ID_ORDENES;
import com.orquestador.demo.utils.Constants.StatusOperation;
import static com.orquestador.demo.utils.Constants.TopicStringConstants.TOPIC_CONFIRMACIONES;
import static com.orquestador.demo.utils.Constants.TopicStringConstants.TOPIC_ERRORES;
import static com.orquestador.demo.utils.Constants.TopicStringConstants.TOPIC_ORQUESTADOR;
import com.orquestador.demo.utils.messages_status.BaseMessage;
import com.orquestador.demo.utils.messages_status.ConfirmationMessage;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;


@Controller
public class ControllerKafkaListener {

      @Autowired
      private WorkSagaService workSagaService;
      @Autowired
      private SagaInstanceService sagaInstanceService;
      @Autowired
      private StepLogSagaService stepLogService;

      private final Logger logger = LoggerFactory.getLogger(ControllerKafkaListener.class);
      private final ObjectMapper objectMapper = new ObjectMapper();


       @KafkaListener(topics = TOPIC_ORQUESTADOR, groupId = GROUP_ID_ORDENES)
       public void listen(String message) {
           logger.info("Mensaje recibido: " + message);
        

           try{
              JsonNode messageJson = objectMapper.readTree(message);
              this.workSagaService.work(messageJson);
           } catch (JsonProcessingException e) {
               logger.error("Error processing message: " + e.getMessage());
           }catch (Exception e){
               logger.error("Unexpected error: " + e.getMessage());
           }
       }

    /************************************************* */
    // Sintaxis de los headers de los mensajes
    // "COMPLETADO_{componentName}_{step}_{numberOfOperation}_{id_step}" para confirmaciones
    // "FALLO_{componentName}_{step}_{numberOfOperation}_{id_step}" En caso de errores se agrega el mensaje de detalle
    /************************************************* */

       @KafkaListener(topics = TOPIC_CONFIRMACIONES, groupId = GROUP_ID_CONFIRMACIONES)
       public void listenConfirm(  @Payload String message) {
           logger.info("Mensaje de confirmación recibido: " + message);
           ConfirmationMessage confirmationMessage = (ConfirmationMessage) convertHeaderToObjectMessage(message);
            this.sagaInstanceService.updateSagaInstanceStatus(confirmationMessage.getNumberOfOperation(), StatusOperation.COMPLETED);
           
            this.stepLogService.updateStepLogStatus(confirmationMessage.getNumberOfOperation(),confirmationMessage.getIdStep(),StatusOperation.COMPLETED);
    
       }


       @KafkaListener(topics = TOPIC_ERRORES, groupId = GROUP_ID_ERRORES)
       public void listenErrors(
                                @Payload String message
                                ) {
           logger.info("Mensaje de error recibido: " + message);
           HandleComponentErrors errorMessageExtracted = (HandleComponentErrors) convertHeaderToObjectMessage(message);
           errorMessageExtracted.setErrorMessage(message);
            this.sagaInstanceService.updateCurrentStepSagaInstance(errorMessageExtracted.getNumberOfOperation(), errorMessageExtracted.getComponentName());
            this.sagaInstanceService.updateSagaInstanceStatus(errorMessageExtracted.getNumberOfOperation(), StatusOperation.FAILED);
            this.stepLogService.updateStepLogStatus(errorMessageExtracted.getNumberOfOperation(),errorMessageExtracted.getIdStep(),StatusOperation.FAILED);
            this.workSagaService.executeCompensate(errorMessageExtracted);
       }

      private BaseMessage convertHeaderToObjectMessage(String message) {
        String[] mensajeSeparado = message.split("_");
        String componentName = mensajeSeparado[1];
        String step = mensajeSeparado[2];
        String numberOfOperation = mensajeSeparado[3];
        String idStep = mensajeSeparado[4];
        
        // Determinar qué tipo de mensaje crear basado en alguna lógica
        if (isErrorMessage(mensajeSeparado)) {
            String errorDetails = mensajeSeparado.length > 5 ? mensajeSeparado[5] : "";
            return new HandleComponentErrors(componentName, step, numberOfOperation, idStep, errorDetails);
        } else {
            return new ConfirmationMessage(componentName, step, numberOfOperation, idStep);
        }
    }
     private boolean isErrorMessage(String[] messageParts) {
     
            return messageParts[0].equals("ERROR") || messageParts.length > 5;
        }
        
}
