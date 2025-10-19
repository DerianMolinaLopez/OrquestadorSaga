package com.orquestador.demo.models;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private String id;

    @Column(name = "saga_id")
    private String sagaId;

    @Column(name = "step")
    private String step;

    @Column(name = "status")
    private String status; // StepStatus

    @Column(columnDefinition = "json")
    private String request;

    @Column(columnDefinition = "json")
    private String response;

    @Column(columnDefinition = "json")
    private String error;

    @Column(name = "attempt")
    private Integer attempt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saga_id", insertable = false, updatable = false)
    private SagaInstance sagaInstance;

    // Constructores
    public SagaStepLog() {}

    public SagaStepLog(String id, String sagaId, String step, String status, Integer attempt) {
        this.id = id;
        this.sagaId = sagaId;
        this.step = step;
        this.status = status;
        this.attempt = attempt;
        this.startedAt = LocalDateTime.now();
    }

    
    // Métodos de utilidad
    public void markAsCompleted(String response) {
        this.status = "COMPLETED";
        this.response = response;
        this.finishedAt = LocalDateTime.now();
    }

    public void markAsFailed(String error, Integer nextAttempt) {
        this.status = "FAILED";
        this.error = error;
        this.attempt = nextAttempt;
        this.finishedAt = LocalDateTime.now();
    }
}