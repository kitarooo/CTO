package com.example.cto.kafka;

import com.example.cto.model.entity.ServiceRequest;
import com.example.cto.model.entity.StatusHistory;
import com.example.cto.model.enums.ServiceRequestStatus;
import com.example.cto.repository.ServiceRequestRepository;
import com.example.cto.repository.StatusHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatusChangeConsumer {

    private final ServiceRequestRepository serviceRequestRepository;
    private final StatusHistoryRepository statusHistoryRepository;

    @Transactional
    @KafkaListener(topics = "work-status",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(StatusChangeEvent event) {

        ServiceRequest serviceRequest = serviceRequestRepository.findById(event.getRequestId())
                .orElse(null);

        StatusHistory history = StatusHistory.builder()
                .changedAt(event.getChangedAt())
                .newStatus(event.getNewStatus())
                .changedBy(event.getChangedBy())
                .someJob(event.getSomeJob())
                .build();
        if (serviceRequest != null) {
            history.setServiceRequest(serviceRequest);
        }

        statusHistoryRepository.save(history);
        if (history.getNewStatus().equals(ServiceRequestStatus.CLOSED)) {
            sendSms();
        }
    }

    private void sendSms() {
        System.out.println("Работник завершил работу!");
    }
}

