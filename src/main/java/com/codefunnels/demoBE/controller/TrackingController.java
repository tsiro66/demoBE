package com.codefunnels.demoBE.controller;

import com.codefunnels.demoBE.dto.TrackingEventDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    @PostMapping("/event")
    public ResponseEntity<Void> trackEvent(@RequestBody TrackingEventDTO eventDTO) {
        System.out.println(
                "--- 📈 ANALYTICS EVENT RECEIVED 📈 ---" + "\n" +
                        "Event: " + eventDTO.getEventName() + "\n" +
                        "Payload: " + eventDTO.getPayload() + "\n" +
                        "Timestamp: " + eventDTO.getTimestamp() + "\n" +
                        "---------------------------------------"
        );

        return ResponseEntity.ok().build();
    }
}
