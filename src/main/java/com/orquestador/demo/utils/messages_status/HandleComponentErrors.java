package com.orquestador.demo.utils.messages_status;



public class HandleComponentErrors extends BaseMessage {
    private String errorMessage;

    public HandleComponentErrors(String componentName, String stepSaga, String numberOfOperation, String errorMessage) {
        super(componentName, stepSaga, numberOfOperation);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    @Override
    public String toString() {
        return "HandleComponentErrors{" +
                "errorMessage='" + errorMessage + '\'' +
                "} " + super.toString();
    }

}
