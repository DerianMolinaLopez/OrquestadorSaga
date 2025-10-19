package com.orquestador.demo.saga;

public class SagaCreator {

    public static SagaStep[] createAllSteps(){

        return new SagaStep[]{
            new DemoSagaStep("1"),
            new DemoSagaStep("2"),
            new DemoSagaStep("3")
        };
    }
    
}
