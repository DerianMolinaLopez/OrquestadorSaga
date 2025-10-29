package com.orquestador.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.orquestador.demo.enums.StatusSagaInstance;
import com.orquestador.demo.exceptions.ConvertJsonNodeToSagaInstanceException;
import com.orquestador.demo.interfaces.SagaInstanceRepository;
import com.orquestador.demo.models.SagaInstance;

@Service
public class SagaInstanceService {
    @Autowired
    private SagaInstanceRepository sagaInstanceRepository;

    public void saveSagaInstance(JsonNode sagaInstance) {
        try {
            SagaInstance sagaInst = convertJsonNodeToSagaInstance(sagaInstance);
            sagaInstanceRepository.save(sagaInst);

        } catch (RuntimeException e) {
            throw new ConvertJsonNodeToSagaInstanceException("Error, no se puede convertir el json al tipo correspondiente a SagaInstance");
        }
        
    }


    private SagaInstance convertJsonNodeToSagaInstance(JsonNode sagaInstanceJson)  throws ConvertJsonNodeToSagaInstanceException {
     
       String correlationId = sagaInstanceJson.get("correlationId").asText();
       String name = sagaInstanceJson.get("clientID").asText();
       String status = StatusSagaInstance.IN_PROGRESS;
       SagaInstance sagaInstance = new SagaInstance( correlationId, name, status);
       return sagaInstance;

    }
    public void updateSagaInstanceStatus(String correlationId, String status){ 
        SagaInstance sagaInstance = sagaInstanceRepository.findByCorrelationId(correlationId);
        sagaInstance.setStatus(status);
        sagaInstanceRepository.save(sagaInstance);
    }//ordenes
    // COMPLETADO_ordenes_1_confirmaciones_a20d3e93-aad9-41dc-9612-b7923178b438

    public void updateCurrentStepSagaInstance(String correlationId, String currentStep){ 
        SagaInstance sagaInstance = sagaInstanceRepository.findByCorrelationId(correlationId);
        sagaInstance.setCurrentStep(currentStep);
        sagaInstanceRepository.save(sagaInstance);
    }
}
