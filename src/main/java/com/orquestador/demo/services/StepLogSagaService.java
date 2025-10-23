package com.orquestador.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.orquestador.demo.exceptions.ExtractJsonNodeValuesToSagaStepLogException;
import com.orquestador.demo.interfaces.SagaStepLogRepository;
import com.orquestador.demo.models.SagaStepLog;

@Service
public class StepLogSagaService {
    private final Logger logger = LoggerFactory.getLogger(StepLogSagaService.class);
    @Autowired
    private SagaStepLogRepository sagaStepLogRepository;

    public void saveStepLog(String correlationId) {
        try {
                extractJsonNodeValuesToSagaStepLog(correlationId);
        } catch (ExtractJsonNodeValuesToSagaStepLogException e) {
            logger.error("Error al convertir el valor JSON a SagaStepLog", e);
            throw e;
        }
    
    }

    public SagaStepLog extractJsonNodeValuesToSagaStepLog(String correlationId) throws ExtractJsonNodeValuesToSagaStepLogException {
        return new SagaStepLog(correlationId, "STEP_NAME", "PENDING");
      
    }

}
