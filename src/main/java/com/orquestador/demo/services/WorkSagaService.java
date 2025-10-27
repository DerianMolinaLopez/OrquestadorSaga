package com.orquestador.demo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.orquestador.demo.constants.FieldNameJson;
import com.orquestador.demo.exceptions.ExecuteStepsException;
import com.orquestador.demo.saga.AplicationSagaContext;
import com.orquestador.demo.saga.SagaStep;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;

@Service
public class WorkSagaService {
    @Autowired
    private SagaInstanceService sagaInstanceService;
    @Autowired
    private StepLogSagaService stepLogSagaService;
    private static final Logger logger = LoggerFactory.getLogger(WorkSagaService.class);
    private final List<SagaStep> steps;

    
    public WorkSagaService(List<SagaStep> steps){
        this.steps = steps;
    }
    public void work(JsonNode payload){
        sagaInstanceService.saveSagaInstance(payload);
        executeSteps(steps, payload);
    }

    private void executeSteps(List<SagaStep> steps, JsonNode payload){
     for(SagaStep step : steps){
         try {
          
            String stepID = stepLogSagaService.saveStepLog(payload.get(FieldNameJson.CORRELATIONID).asText(),step.getStepName());
            step.execute(new AplicationSagaContext(payload.get(FieldNameJson.CORRELATIONID).asText(), payload,stepID));
            sagaInstanceService.updateCurrentStepSagaInstance(payload.get(FieldNameJson.CORRELATIONID).asText(), step.getStepName());
         } catch (ExecuteStepsException e) {
            logger.info("Error executing step: " + e.getMessage());
            throw new ExecuteStepsException("Error al ejecutar el paso " + step.getStepName());
         }
     }   
    }

    public void executeCompensate(HandleComponentErrors errorContext){
           for(SagaStep step : steps){
         try {
              step.compensate(errorContext);
         } catch (ExecuteStepsException e) {
            logger.info("Error executing step: " + e.getMessage());
            throw new ExecuteStepsException("Error al ejecutar el paso " + step.getStepName());
         }
     }   
    }
}
