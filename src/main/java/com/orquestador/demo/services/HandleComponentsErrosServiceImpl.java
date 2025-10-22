package com.orquestador.demo.services;

import org.springframework.stereotype.Component;

import com.orquestador.demo.interfaces.HandleComponentsErrorsService;
import com.orquestador.demo.utils.messages_status.HandleComponentErrors;

@Component
public class HandleComponentsErrosServiceImpl implements HandleComponentsErrorsService {
    @Override
    public void handleError(HandleComponentErrors error) {
  
        System.out.println("Error en el componente: " + error.getComponentName());
        System.out.println("Mensaje de error: " + error.getErrorMessage());
        System.out.println("Número de operación: " + error.getNumberOfOperation());
    }
    
}
