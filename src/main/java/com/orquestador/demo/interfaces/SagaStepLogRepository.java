package com.orquestador.demo.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orquestador.demo.models.SagaStepLog;

@Repository
public interface SagaStepLogRepository extends JpaRepository<SagaStepLog, String> {
       @Query("""
        SELECT s 
        FROM SagaStepLog s 
        WHERE s.correlationId = :id
    """)
    Optional<SagaStepLog> findByCorrelationId(@Param("id") String id);

    
    
}
