package com.orquestador.demo.utils.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orquestador.demo.controller.ControllerKafkaListener;

public class TopicStringConstants {
      public static final Logger logger = LoggerFactory.getLogger(ControllerKafkaListener.class);
      public static final String TOPIC_ORQUESTADOR = "orquestador";
      public static final String TOPIC_CONFIRMACIONES = "confirmaciones";
      public static final String TOPIC_ERRORES = "errores";
}
