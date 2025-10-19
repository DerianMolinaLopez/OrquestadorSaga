package com.orquestador.demo;
import org.springframework.boot.test.context.SpringBootTest;

import com.orquestador.demo.services.WorkSagaService;
@SpringBootTest
public class WorkSagaServiceTest {

    private WorkSagaService workSagaService;

    void contextLoads() {

        workSagaService = new WorkSagaService();
       

      
    }
    
}
