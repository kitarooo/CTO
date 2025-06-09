package com.example.cto.model.entity;

import com.example.cto.model.entity.ServiceRequest;
import com.example.cto.model.enums.ServiceRequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "status_histories")
@NoArgsConstructor
@AllArgsConstructor
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private ServiceRequestStatus newStatus;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changedAt;

    @Column(name = "some_job")
    private String someJob;

    @Column(name = "changed_by", nullable = false)
    private String changedBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "service_request_id")
    private ServiceRequest serviceRequest;
}

