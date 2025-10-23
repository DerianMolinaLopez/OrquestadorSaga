package com.orquestador.demo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

@Component
@Order(3)
public class PaymentStepSaga implements SagaStep{

    private static final Logger logger = LoggerFactory.getLogger(PaymentStepSaga.class);


   @Override
    public void execute() throws SagaStepExcecutionException{
        logger.info("Ejecutando PaymentStepSaga");

    }
    @Override
    public void compensate() throws SagaStepCompensateException{
        logger.info("Compensando PaymentStepSaga");

    }

}
