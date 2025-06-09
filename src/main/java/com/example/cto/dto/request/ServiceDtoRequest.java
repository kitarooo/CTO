package com.example.cto.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDtoRequest {
    private String someJob;
    private Long carId;
}
