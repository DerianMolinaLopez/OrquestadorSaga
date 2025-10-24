package com.orquestador.demo.saga;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;

public interface SagaStep {

    public void execute(AplicationSagaContext ctx) throws SagaStepExcecutionException;

    public void compensate(HandleComponentErrors errorContext) throws SagaStepCompensateException;

    public String getStepName();


}
