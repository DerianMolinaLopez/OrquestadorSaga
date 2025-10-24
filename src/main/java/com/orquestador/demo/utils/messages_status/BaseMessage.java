package com.orquestador.demo.utils.messages_status;

import lombok.Data;

@Data
public abstract class BaseMessage {
    private String ComponentName;
    private String NumberOfOperation;
    private String StepSaga;
    private String IdStep;

    public BaseMessage(String componentName, String stepSaga, String numberOfOperation, String idStep) {
        this.ComponentName = componentName;
        this.StepSaga = stepSaga;
        this.NumberOfOperation = numberOfOperation;
        this.IdStep = idStep;
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
