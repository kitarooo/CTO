package com.example.cto.service.impl;

import com.example.cto.dto.request.ServiceDtoRequest;
import com.example.cto.dto.response.ServiceRequestListResponse;
import com.example.cto.error.exceptions.AccessDeniedException;
import com.example.cto.error.exceptions.RequestAlreadyExistsException;
import com.example.cto.error.exceptions.NotFoundException;
import com.example.cto.kafka.StatusChangeEvent;
import com.example.cto.mapper.ServiceRequestMapper;
import com.example.cto.model.entity.Car;
import com.example.cto.model.entity.ServiceRequest;
import com.example.cto.model.entity.User;
import com.example.cto.model.enums.ServiceRequestStatus;
import com.example.cto.repository.CarRepository;
import com.example.cto.repository.ServiceRequestRepository;
import com.example.cto.service.ServiceRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final CarRepository carRepository;
    private final ServiceRequestMapper serviceRequestMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    @Transactional
    public Page<ServiceRequestListResponse> getListByStatus(ServiceRequestStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ServiceRequest> requestPage = serviceRequestRepository.findByServiceRequestStatus(status, pageable);
        return requestPage.map(serviceRequestMapper::toDto);
    }


    @Override
    @Transactional
    public String createServiceRequest(ServiceDtoRequest serviceDtoRequest) {
        Car car = carRepository.findById(serviceDtoRequest.getCarId())
                .orElseThrow(() -> new NotFoundException("Автомобиль с ID " + serviceDtoRequest.getCarId() + " не найден"));
        User user = getAuthUser();

        if (!car.getUser().getId().equals(user.getId())) {
            System.out.println(car.getUser().getEmail());
            System.out.println(user.getEmail());
            throw new AccessDeniedException("Автомобиль не принадлежит вам!");
        }
        Optional<ServiceRequest> existingRequest = serviceRequestRepository.findByCarAndServiceRequestStatusNot(
                car, ServiceRequestStatus.CLOSED);

        if (existingRequest.isPresent()) {
            throw new RequestAlreadyExistsException("Заявка с автомобилем \"" + car.getName() + "\" уже создана и не закрыта");
        }

        ServiceRequest serviceRequest = ServiceRequest.builder()
                .serviceRequestStatus(ServiceRequestStatus.NEW)
                .someJob(serviceDtoRequest.getSomeJob())
                .car(car)
                .user(user)
                .build();
        serviceRequestRepository.save(serviceRequest);

        StatusChangeEvent event = new StatusChangeEvent(
                serviceRequest.getId(),
                serviceRequest.getServiceRequestStatus(),
                LocalDateTime.now(),
                serviceRequest.getSomeJob(),
                user.getFirstname()
        );
        kafkaTemplate.send("work-status", event.getRequestId().toString(), event);

        return "Заявка успешно создана";
    }


    @Override
    @Transactional
    public Page<ServiceRequestListResponse> getListByUser(int page, int size) {
        User user = getAuthUser();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ServiceRequest> requestPage = serviceRequestRepository.findByUser(user, pageable);

        return requestPage.map(serviceRequestMapper::toDto);
    }

    @Override
    @Transactional
    public String acceptServiceRequest(Long serviceRequestId) {
        StatusChangeEvent event = buildAndSaveServiceRequest(serviceRequestId, ServiceRequestStatus.ACCEPTED);
        kafkaTemplate.send("work-status", event.getRequestId().toString(), event);

        return "Задача успешно принята";
    }

    @Override
    @Transactional
    public String processingServiceRequest(Long serviceRequestId) {
        StatusChangeEvent event = buildAndSaveServiceRequest(serviceRequestId, ServiceRequestStatus.IN_PROGRESS);
        kafkaTemplate.send("work-status", event.getRequestId().toString(), event);

        return "Выполнение работы";
    }


    @Override
    @Transactional
    public String repairWork(Long serviceRequestId) {
        StatusChangeEvent event = buildAndSaveServiceRequest(serviceRequestId, ServiceRequestStatus.REPAIR_COMPLETED);
        kafkaTemplate.send("work-status", event.getRequestId().toString(), event);

        return "Задача выполнена";
    }

    @Override
    @Transactional
    public String finishWork(Long serviceRequestId) {
        StatusChangeEvent event = buildAndSaveServiceRequest(serviceRequestId, ServiceRequestStatus.CLOSED);
        kafkaTemplate.send("work-status", event.getRequestId().toString(), event);

        return "Задача успешно закрыта";
    }

    private StatusChangeEvent buildAndSaveServiceRequest(Long serviceRequestId, ServiceRequestStatus status) {
        User user = getAuthUser();
        ServiceRequest serviceRequest = serviceRequestRepository.findById(serviceRequestId)
                .orElseThrow(() -> new NotFoundException("Заявка не найдена"));
        serviceRequest.setServiceRequestStatus(status);
        serviceRequestRepository.save(serviceRequest);

        return new StatusChangeEvent(
                serviceRequest.getId(),
                serviceRequest.getServiceRequestStatus(),
                LocalDateTime.now(),
                serviceRequest.getSomeJob(),
                user.getFirstname()
        );
    }

    private User getAuthUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
