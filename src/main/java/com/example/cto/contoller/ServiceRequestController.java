package com.example.cto.contoller;

import com.example.cto.dto.request.ServiceDtoRequest;
import com.example.cto.dto.response.ServiceRequestListResponse;
import com.example.cto.error.handler.ExceptionResponse;
import com.example.cto.model.enums.ServiceRequestStatus;
import com.example.cto.service.ServiceRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/service-requests")
@RequiredArgsConstructor
public class ServiceRequestController {
    private final ServiceRequestService serviceRequestService;

    @PostMapping("/create")
    @Operation(summary = "Cоздание заявки в СТО",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "400", description = "Request already exist exception!"
                    )
            })
    public String createServiceRequest(@RequestBody ServiceDtoRequest serviceDtoRequest) {
        return serviceRequestService.createServiceRequest(serviceDtoRequest);
    }

    @GetMapping("/requests")
    @Operation(summary = "Получение заявок по статусу")
    public Page<ServiceRequestListResponse> getListByStatus(@RequestParam ServiceRequestStatus status,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return serviceRequestService.getListByStatus(status, page, size);
    }

    @GetMapping("/user")
    @Operation(summary = "Получение заявок по пользователю")
    public Page<ServiceRequestListResponse> getListByUser(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        return serviceRequestService.getListByUser(page, size);
    }

    @PostMapping("/accept/{id}")
    @Operation(summary = "Принятие заявки",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good")
            })
    public String acceptServiceRequest(@PathVariable Long id) {
        return serviceRequestService.acceptServiceRequest(id);
    }

    @PostMapping("/processing/{id}")
    @Operation(summary = "Начало работы",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good")
            })
    public String processingServiceRequest(@PathVariable Long id) {
        return serviceRequestService.processingServiceRequest(id);
    }

    @PostMapping("/repair/{id}")
    @Operation(summary = "Завершение задач",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good")
            })
    public String repairServiceRequest(@PathVariable Long id) {
        return serviceRequestService.repairWork(id);
    }

    @PostMapping("/finish/{id}")
    @Operation(summary = "Закрытие заявки",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good")
            })
    public String finishServiceRequest(@PathVariable Long id) {
        return serviceRequestService.finishWork(id);
    }

}
