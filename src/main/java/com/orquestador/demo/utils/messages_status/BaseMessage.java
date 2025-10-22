package com.orquestador.demo.utils.messages_status;

import lombok.Data;

@Data
public abstract class BaseMessage {
    private String ComponentName;
    private String NumberOfOperation;
    private String StepSaga;

    public BaseMessage(String componentName, String stepSaga, String numberOfOperation) {
        this.ComponentName = componentName;
        this.StepSaga = stepSaga;
        this.NumberOfOperation = numberOfOperation;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "ComponentName='" + ComponentName + '\'' +
                ", NumberOfOperation='" + NumberOfOperation + '\'' +
                ", StepSaga='" + StepSaga + '\'' +
                '}';
    }
    
}
