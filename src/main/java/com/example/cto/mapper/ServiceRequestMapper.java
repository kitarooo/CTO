package com.example.cto.mapper;

import com.example.cto.dto.response.ServiceRequestListResponse;
import com.example.cto.model.entity.ServiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceRequestMapper {
    @Mapping(source = "user.firstname", target = "firstname")
    @Mapping(source = "car.name", target = "carName")
    ServiceRequestListResponse toDto(ServiceRequest serviceRequest);
}
