package com.mycompany.tracking.controller;

import com.mycompany.tracking.dto.TrackingNumberRequest;
import com.mycompany.tracking.dto.TrackingNumberResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mycompany.tracking.service.TrackingNumberService;


import java.util.UUID;

@RestController
@RequestMapping("/api")
public class TrackingNumberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackingNumberController.class);

    private final TrackingNumberService trackingNumberService;

    @Autowired
    public TrackingNumberController(TrackingNumberService trackingNumberService) {
        this.trackingNumberService = trackingNumberService;
    }

    @PostMapping("/next-tracking-number")
    public TrackingNumberResponse nextTrackingNumber(@Valid @RequestBody TrackingNumberRequest request,
                                                     @RequestHeader(value = "X-Correlation-ID", required = false) String correlationId) {
        String traceId = (correlationId != null) ? correlationId : UUID.randomUUID().toString();
        LOGGER.info("[TraceID: {}] Request received for tracking number generation: {}", traceId, request);

        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request, traceId);
        LOGGER.info("[TraceID: {}] Generated TrackingNumberResponse: {}", traceId, response);
        return response;
    }
}
