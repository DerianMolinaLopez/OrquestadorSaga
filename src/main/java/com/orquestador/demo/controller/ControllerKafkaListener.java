package com.orquestador.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

import static com.orquestador.demo.utils.Constants.PrefixStringConstants.*;
import static com.orquestador.demo.utils.Constants.TopicStringConstants.*;
import static com.orquestador.demo.utils.Constants.GroupIdStringConstants.*;


import com.orquestador.demo.interfaces.HandleComponentsErrorsService;
import com.orquestador.demo.utils.messages_status.BaseMessage;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.orquestador.demo.utils.messages_status.ConfirmationMessage;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;

    /************************************************* */
    // Sintaxis de los headers de los mensajes
    // "COMPLETADO_{componentName}_{step}_{numberOfOperation}" para confirmaciones
    // "FALLO_{componentName}_{step}_{numberOfOperation}" En caso de errores se agrega el mensaje de detalle
    /************************************************* */
@Controller
public class ControllerKafkaListener {
      @Autowired
      private HandleComponentsErrorsService handleComponentsErrorsService;
      private final Logger logger = LoggerFactory.getLogger(ControllerKafkaListener.class);


       @KafkaListener(topics = TOPIC_ORQUESTADOR, groupId = GROUP_ID_ORDENES)
       public void listen(String message) {
           logger.info("Mensaje recibido: " + message);
           //flujo del orquestador
       }

       @KafkaListener(topics = TOPIC_CONFIRMACIONES, groupId = GROUP_ID_CONFIRMACIONES)
       public void listenConfirm( @Header("Kafka_Header_Operation") String operation, @Payload String message) {
           logger.info("Mensaje de confirmación recibido: " + message);
           ConfirmationMessage confirmationMessage = (ConfirmationMessage) convertHeaderToOObjectMessage(operation,PREFIX_COMPLETADO);

           //flujo del servicio de confirmacion
       }

       @KafkaListener(topics = TOPIC_ERRORES, groupId = GROUP_ID_ERRORES)
       public void listenErrors(@Header("Kafka_Header_Operation") String operation,
                                @Payload String message,
                                @Header("errorMessage") String errorMessage
                                ) {
           logger.info("Mensaje de error recibido: " + message);
           HandleComponentErrors errorMessageExtracted = (HandleComponentErrors) convertHeaderToOObjectMessage(operation,PREFIX_FALLO);
              errorMessageExtracted.setErrorMessage(message);
      
              

           //flujo del servicio de errores
       }

       private BaseMessage convertHeaderToOObjectMessage(String header,String status){
        
           String[] mensajeSeparado = header.split("_");
           String componentName = mensajeSeparado[1];
           String step = mensajeSeparado[2];
           String numberOfOperation = mensajeSeparado[3];
           if (status.equals(PREFIX_COMPLETADO)) {
               return new ConfirmationMessage(componentName, step, numberOfOperation);
           }else{

           return new HandleComponentErrors(componentName, step, numberOfOperation, null); 
           }


       }
        
}
