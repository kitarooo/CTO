package com.example.cto.model.entity;

import com.example.cto.model.entity.base_entity.BaseEntity;
import com.example.cto.model.enums.ServiceRequestStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_requests")
public class ServiceRequest extends BaseEntity {

    @Column(name = "SOME_JOB")
    private String someJob;

    @Column(name = "service_request_status")
    @Enumerated(EnumType.STRING)
    private ServiceRequestStatus serviceRequestStatus;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private User user;

    @JoinColumn(name = "car_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private Car car;

}
