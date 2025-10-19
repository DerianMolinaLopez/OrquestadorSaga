package com.orquestador.demo.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class ControllerKafkaListener {
       @KafkaListener(topics = "orquestador", groupId = "ordenes")
       public void listen(String message) {
           System.out.println("Mensaje recibbido: " + message);
       }
}
