package com.orquestador.demo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.orquestador.demo.exceptions.ExecuteStepsException;
import com.orquestador.demo.saga.SagaStep;

@Service
public class WorkSagaService {
    private static final Logger logger = LoggerFactory.getLogger(WorkSagaService.class);
    private final List<SagaStep> steps; 
    
    public WorkSagaService(List<SagaStep> steps){
        this.steps = steps;
    }
    public void work(){
        executeSteps(steps);
    }

    private void executeSteps(List<SagaStep> steps){
     for(SagaStep step : steps){
         try {
            step.execute();
         } catch (ExecuteStepsException e) {
            logger.info("Error executing step: " + e.getMessage());
            step.compensate();
         }
     }   
    }
}
