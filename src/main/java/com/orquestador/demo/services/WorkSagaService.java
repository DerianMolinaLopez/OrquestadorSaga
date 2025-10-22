package com.orquestador.demo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.orquestador.demo.exceptions.ExecuteStepsException;
import com.orquestador.demo.saga.SagaCreator;
import com.orquestador.demo.saga.SagaStep;

@Service
public class WorkSagaService {
    private static final Logger logger = LoggerFactory.getLogger(WorkSagaService.class);
    public void work(){
        logger.info("Iniciando con la creacion de los pasos de operacion");
        SagaStep[] steps = getAllSteps();
        executeSteps(steps);
        logger.info("Ejecucion de los pasos terminada, esperando la confirmacion para el numero de operacion: [COLOCAR NUMERO DE OPERACION]");

    }
    private SagaStep[] getAllSteps(){
        return SagaCreator.createAllSteps();
    }
    private void executeSteps(SagaStep[] steps){
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
