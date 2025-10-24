package com.orquestador.demo.services;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orquestador.demo.exceptions.ExtractJsonNodeValuesToSagaStepLogException;
import com.orquestador.demo.interfaces.SagaStepLogRepository;
import com.orquestador.demo.models.SagaStepLog;
import com.orquestador.demo.utils.Constants.StatusOperation;

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
        return new SagaStepLog(correlationId, stepName, StatusOperation.IN_PROGRESS);

    }

    //TODO: estoy enviando 3 variables, lo recomendable sera dejarlo asi o guardar todo en un objeto 

    public void updateStepLogStatus(String correlationId, String stepId,String status) {
        try {
                LocalDateTime fechaHoraActual = LocalDateTime.now();

                 SagaStepLog stepLog = sagaStepLogRepository.findByCorrelationIdAndStepName(stepId);
            if (stepLog != null) {

                stepLog.setFinishedAt(fechaHoraActual);
                stepLog.setStatus(status);
                sagaStepLogRepository.save(stepLog);
            } else {
                logger.error("No se encontró el registro del paso para actualizar. CorrelationId: {}, StepID: {}", correlationId, stepId);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar el estado del registro del paso", e);
            throw e;
        }
    }

}
