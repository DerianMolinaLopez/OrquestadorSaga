package com.orquestador.demo.controller;


import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
@Controller
public class ControllerKafkaPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ControllerKafkaPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void publish(String message,String topic) {
        kafkaTemplate.send(topic, message);
    }

    public void publishWithHeaders(String message, String topic, Map<String, Object> headers) {
        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .copyHeaders(headers) 
                .build();

        kafkaTemplate.send(msg);
    }
   
   
    
}
