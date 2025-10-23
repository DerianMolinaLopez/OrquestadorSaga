package com.orquestador.demo.models;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "saga_instances")
public class SagaInstance {

    @Id
    @Column(name = "correlation_id")
    private String correlationId;

    @Column(name = "name")
    private String name; 

    @Column(name = "status")
    private String status; 

    @Column(name = "current_step")
    private String currentStep;

    @Column(columnDefinition = "json")
    private String data = "{}";

    @Column(name = "processed_message_ids", columnDefinition = "json")
    private String processedMessageIds = "[]";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "last_error", columnDefinition = "json")
    private String lastError;

    @OneToMany(mappedBy = "sagaInstance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SagaStepLog> stepLogs = new ArrayList<>();

    // Constructores
    public SagaInstance() {}

    public SagaInstance( String correlationId, String name, String status) {

        this.correlationId = correlationId;
        this.name = name;
        this.status = status;
    }

 
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}