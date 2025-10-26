package com.orquestador.demo.utils.messages_status;

public class ConfirmationMessage extends BaseMessage {
    public ConfirmationMessage(String componentName, String stepSaga, String numberOfOperation, String idStep) {
        super(componentName, stepSaga, numberOfOperation, idStep);
    }
    
}
