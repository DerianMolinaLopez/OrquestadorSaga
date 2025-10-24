package com.orquestador.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orquestador.demo.models.SagaInstance;

@Repository
public interface SagaInstanceRepository extends JpaRepository<SagaInstance, String> {
    SagaInstance findByCorrelationId(String correlationId);
    
}
