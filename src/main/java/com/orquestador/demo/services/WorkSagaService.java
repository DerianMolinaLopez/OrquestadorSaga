package com.orquestador.demo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
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
            //TODO: refactorizar las cadenas magicas y manejar todo en una variable de "correlationId"
            //TODO: tambien enviar el numero de paso que le corresponde, por que peude haber un mismo paso, pero se requiere el especifico que fue registrado en operacion

            stepLogSagaService.saveStepLog(payload.get("correlationId").asText(),step.getStepName());
            step.execute(new AplicationSagaContext(payload.get("correlationId").asText(), payload));
            sagaInstanceService.updateCurrentStepSagaInstance(payload.get("correlationId").asText(), step.getStepName());
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
