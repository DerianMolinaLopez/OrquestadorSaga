package com.orquestador.demo.saga;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

public interface SagaStep {

    public void execute() throws SagaStepExcecutionException;

    public void compensate() throws SagaStepCompensateException;


}
