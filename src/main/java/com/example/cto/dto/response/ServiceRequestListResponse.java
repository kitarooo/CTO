package com.example.cto.dto.response;

import com.example.cto.model.entity.Car;
import com.example.cto.model.enums.ServiceRequestStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestListResponse {
    private Long id;
    private String someJob;
    private ServiceRequestStatus serviceRequestStatus;
    private String firstname;
    private String carName;
}
