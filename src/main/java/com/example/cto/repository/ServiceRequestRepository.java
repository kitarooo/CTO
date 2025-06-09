package com.example.cto.repository;

import com.example.cto.model.entity.Car;
import com.example.cto.model.entity.ServiceRequest;
import com.example.cto.model.entity.User;
import com.example.cto.model.enums.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    Page<ServiceRequest> findByServiceRequestStatus(ServiceRequestStatus status, Pageable pageable);
    Page<ServiceRequest> findByUser(User user, Pageable pageable);
    Optional<ServiceRequest> findByCarAndServiceRequestStatusNot(Car car, ServiceRequestStatus status);

}
