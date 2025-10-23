package com.orquestador.demo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

@Component
@Order(2)
public class OrderStepSaga implements  SagaStep{
    private static final Logger logger = LoggerFactory.getLogger(OrderStepSaga.class);

  
    @Override
    public void execute() throws SagaStepExcecutionException{
        logger.info("Ejecutando OrderStepSaga");

    }
    @Override
    public void compensate() throws SagaStepCompensateException{
        logger.info("Compensando OrderStepSaga");

    }
    
}
