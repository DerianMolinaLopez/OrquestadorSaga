package com.orquestador.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orquestador.demo.models.SagaStepLog;

@Repository
public interface SagaStepLogRepository extends JpaRepository<SagaStepLog, String> {

    SagaStepLog findByCorrelationIdAndStepName( String idStep);

    
    
}
