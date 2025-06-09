package com.example.cto.service;

import com.example.cto.dto.request.ServiceDtoRequest;
import com.example.cto.dto.response.ServiceRequestListResponse;
import com.example.cto.model.enums.ServiceRequestStatus;
import org.springframework.data.domain.Page;

public interface ServiceRequestService {

    Page<ServiceRequestListResponse> getListByStatus(ServiceRequestStatus status, int page, int size);
    String createServiceRequest(ServiceDtoRequest serviceDtoRequest);
    Page<ServiceRequestListResponse> getListByUser(int page, int size);
    String acceptServiceRequest(Long serviceRequestId);
    String processingServiceRequest(Long serviceRequestId);
    String repairWork(Long serviceRequestId);
    String finishWork(Long serviceRequestId);
}
