package com.orquestador.demo.saga;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.orquestador.demo.exceptions.SagaStepCompensateException;
import com.orquestador.demo.exceptions.SagaStepExcecutionException;

@Component
@Order(1)
public class InventoryStepSaga implements SagaStep{
      private static final Logger logger = LoggerFactory.getLogger(InventoryStepSaga.class);

  

     @Override
    public void execute() throws SagaStepExcecutionException{
        logger.info("Ejecutando InventoryStepSaga");

    }
    @Override
    public void compensate() throws SagaStepCompensateException{
        logger.info("Compensando InventoryStepSaga");

    }
 }


