package com.orquestador.demo.exceptions;

public class SagaStepCompensateException extends RuntimeException {
    public SagaStepCompensateException(String message) {
        super(message);
    }
    
}
