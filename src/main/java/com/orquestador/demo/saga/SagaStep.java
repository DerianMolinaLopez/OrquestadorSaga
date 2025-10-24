package com.orquestador.demo.saga;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

public interface SagaStep {

    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException;

    public void compensate() throws SagaStepCompensateException;

    public String getStepName();


}
