package com.orquestador.demo.saga;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

import lombok.Data;
@Data
public abstract class SagaStep {
    private String operationId;
   

    public SagaStep(String operationId) {
        this.operationId = operationId;
    }

    public void execute() throws SagaStepExcecutionException{

    }
    public void compensate() throws SagaStepCompensateException{

    }
    
}
