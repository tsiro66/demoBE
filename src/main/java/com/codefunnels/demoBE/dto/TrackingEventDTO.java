package com.codefunnels.demoBE.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class TrackingEventDTO {
    private String eventName;
    private Map<String, Object> payload;
    private String timestamp;
}
