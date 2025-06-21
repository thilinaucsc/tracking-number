package com.mycompany.tracking.service;

import com.mycompany.tracking.dto.TrackingNumberRequest;
import com.mycompany.tracking.dto.TrackingNumberResponse;
import com.mycompany.tracking.entity.TrackingNumberEntity;
import com.mycompany.tracking.exception.TrackingException;
import com.mycompany.tracking.repository.TrackingNumberRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {

    private static final Logger logger = LoggerFactory.getLogger(TrackingNumberServiceImpl.class);

    private final TrackingNumberRepository trackingNumberRepository;

    public TrackingNumberServiceImpl(TrackingNumberRepository trackingNumberRepository) {
        this.trackingNumberRepository = trackingNumberRepository;
    }

    public TrackingNumberResponse generateTrackingNumber(@Valid TrackingNumberRequest request, String traceId) {
        try {
            // Include traceId in logs for better tracking
            logger.info("Trace ID: {} - Generating tracking number for customer {}", traceId, request.customerName());

            // Validation for invalid country code
            if (!isValidCountryCode(request.originCountryId()) || !isValidCountryCode(request.destinationCountryId())) {
                throw new TrackingException("Invalid country code", new Throwable());
            }

            // Validation for invalid weight
            if (request.weight() <= 0) {
                throw new TrackingException("Invalid weight", new Throwable());
            }

            // Generate raw string using request parameters
            StringBuilder raw = new StringBuilder(request.originCountryId() + request.destinationCountryId() + request.weight() + request.createdAt().toString() +
                    request.customerId() + request.customerSlug());

            // Generate SHA-256 hash and create tracking number
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.toString().getBytes(StandardCharsets.UTF_8));
            String base64 = Base64.getUrlEncoder().encodeToString(hash);
            String tracking = base64.replaceAll("[^A-Z0-9]", "").substring(0, 16);

            // Check if the tracking number already exists in the database
            Optional<TrackingNumberEntity> existingTracking = trackingNumberRepository.findByTrackingNumber(tracking);

            // Retry generating a new tracking number if a duplicate exists
            int retries = 3;
            while (existingTracking.isPresent() && retries > 0) {
                logger.info("Trace ID: {} - Duplicate tracking number detected, regenerating", traceId);
                // Modify raw string or hash to ensure uniqueness
                raw.append(UUID.randomUUID());  // Adding UUID to the raw string for uniqueness
                hash = digest.digest(raw.toString().getBytes(StandardCharsets.UTF_8));
                base64 = Base64.getUrlEncoder().encodeToString(hash);
                tracking = base64.replaceAll("[^A-Z0-9]", "").substring(0, 16);

                // Decrement retry counter
                retries--;
                existingTracking = trackingNumberRepository.findByTrackingNumber(tracking);  // Check again
            }

            // If we've run out of retries, throw an exception
            if (existingTracking.isPresent()) {
                throw new TrackingException("Failed to generate a unique tracking number after retries.",
                        new IllegalStateException("Duplicate tracking number detected multiple times"));
            }

            TrackingNumberEntity trackingNumberEntity = TrackingNumberEntity.builder().trackingNumber(tracking).customerId(request.customerId())
                    .destinationCountryId(request.destinationCountryId()).weight(request.weight()).originCountryId(request.originCountryId())
                    .correlationId(traceId).createdAt(request.createdAt().toString()).build();

            trackingNumberRepository.save(trackingNumberEntity);

            TrackingNumberResponse response = new TrackingNumberResponse(tracking, request.createdAt());

            // if you use Kafka, Publish a Kafka event with the tracking number
            //
            // trackingNumberProducer.sendTrackingNumberEvent(response);

            logger.info("Trace ID: {} - Generated tracking number: {}", traceId, tracking);

            return response;
        } catch (TrackingException e) {
            throw new TrackingException(e.getMessage(), e);
        }catch (Exception e ) {
            logger.error("Trace ID: {} - Failed to generate tracking number", traceId, e);
            throw new TrackingException("Failed to generate tracking number", e);
        }
    }

    private boolean isValidCountryCode(String countryCode) {
        // Example validation logic for country code
        return countryCode != null && countryCode.length() == 2 && countryCode.matches("[A-Z]+");
    }
}