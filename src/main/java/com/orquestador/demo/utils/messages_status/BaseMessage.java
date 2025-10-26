package com.orquestador.demo.utils.messages_status;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class BaseMessage {
    private String ComponentName;
    private String NumberOfOperation;
    private String StepSaga;
    private String IdStep;
    private LocalDateTime date;

    public BaseMessage(String componentName, String stepSaga, String numberOfOperation, String idStep) {
        this.ComponentName = componentName;
        this.StepSaga = stepSaga;
        this.NumberOfOperation = numberOfOperation;
        this.IdStep = idStep;
        this.date = LocalDateTime.now();
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
