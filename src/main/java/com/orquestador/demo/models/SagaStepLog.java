package com.orquestador.demo.models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "saga_step_logs")
public class SagaStepLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "step")
    private String step;

    @Column(name = "status")
    private String status; 

 
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correlation_id", insertable = false, updatable = false)
    private SagaInstance sagaInstance;

    // Constructores
    public SagaStepLog() {}

    public SagaStepLog( String correlationId, String step, String status) {
      
        this.correlationId = correlationId;
        this.step = step;
        this.status = status;
    
        this.startedAt = LocalDateTime.now();
    }

    
    // Métodos de utilidad para marcarlo a como avanza el tiempo
    public void markAsCompleted() {
        this.status = "COMPLETED";

        this.finishedAt = LocalDateTime.now();
    }

    public void markAsFailed(String error) {
        this.status = "FAILED";
     
        this.finishedAt = LocalDateTime.now();
    }
}