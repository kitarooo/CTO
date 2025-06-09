package com.example.cto.kafka;

import com.example.cto.model.enums.ServiceRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangeEvent {
    private Long requestId;
    private ServiceRequestStatus newStatus;
    private LocalDateTime changedAt;
    private String someJob;
    private String changedBy;
}

