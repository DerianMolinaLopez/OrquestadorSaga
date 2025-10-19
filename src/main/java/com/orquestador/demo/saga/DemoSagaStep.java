package com.orquestador.demo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

public class DemoSagaStep extends SagaStep {
     private static final Logger logger = LoggerFactory.getLogger(DemoSagaStep.class);
    public DemoSagaStep(String operationId) {
        super(operationId);
    }

    @Override
    public void compensate() throws SagaStepCompensateException {
        logger.info("Compensating DemoSagaStep with operationId: " + this.getOperationId());
    }

    @Override
    public void execute() throws SagaStepExcecutionException {
        logger.info("Executing DemoSagaStep with operationId: " + this.getOperationId());
    }

    

}
