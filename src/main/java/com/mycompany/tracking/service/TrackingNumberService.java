package com.mycompany.tracking.service;

import com.mycompany.tracking.dto.TrackingNumberRequest;
import com.mycompany.tracking.dto.TrackingNumberResponse;

public interface TrackingNumberService {
    TrackingNumberResponse generateTrackingNumber(TrackingNumberRequest request, String traceId);
}
