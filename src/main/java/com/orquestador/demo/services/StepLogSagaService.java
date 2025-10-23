package com.orquestador.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orquestador.demo.exceptions.ExtractJsonNodeValuesToSagaStepLogException;
import com.orquestador.demo.interfaces.SagaStepLogRepository;
import com.orquestador.demo.models.SagaStepLog;

@Service
public class StepLogSagaService {
    private final Logger logger = LoggerFactory.getLogger(StepLogSagaService.class);

    @Autowired
    private SagaStepLogRepository sagaStepLogRepository;

    public void saveStepLog(String correlationId,String stepName){
        try {
                SagaStepLog stepLog = createStepLogInstance(correlationId, stepName);
                sagaStepLogRepository.save(stepLog);
              
        } catch (ExtractJsonNodeValuesToSagaStepLogException e) {
            logger.error("Error al guardar el registro del paso", e);
            throw e;
        }
    
    }

    public SagaStepLog createStepLogInstance(String correlationId, String stepName) throws ExtractJsonNodeValuesToSagaStepLogException {
        return new SagaStepLog(correlationId, stepName, "PENDING");

    }

}
