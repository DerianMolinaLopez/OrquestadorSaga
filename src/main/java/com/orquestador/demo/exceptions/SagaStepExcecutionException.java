package com.orquestador.demo.exceptions;

public class SagaStepExcecutionException  extends RuntimeException {
    public SagaStepExcecutionException(String message) {
        super(message);
    }
    
}
