package com.mycompany.tracking.dto;

import java.time.OffsetDateTime;

public record TrackingNumberResponse (
    String trackingNumber,
        OffsetDateTime createdAt
) {}
