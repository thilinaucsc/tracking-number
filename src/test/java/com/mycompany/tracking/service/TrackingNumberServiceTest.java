package com.mycompany.tracking.service;

import com.mycompany.tracking.dto.TrackingNumberRequest;
import com.mycompany.tracking.dto.TrackingNumberResponse;
import com.mycompany.tracking.entity.TrackingNumberEntity;
import com.mycompany.tracking.exception.TrackingException;
import com.mycompany.tracking.repository.TrackingNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Enable Mockito for JUnit 5
public class TrackingNumberServiceTest {

    @Mock
    private TrackingNumberRepository trackingNumberRepository;  // Mock the repository

    @InjectMocks
    private TrackingNumberServiceImpl trackingNumberService;

    @Test
    void testGenerateTrackingNumber_invalidCountryCode() {
        TrackingNumberRequest request = new TrackingNumberRequest(
                "INVALID", "ID", 1.234, OffsetDateTime.now(), UUID.randomUUID(), "Invalid Customer", "invalid-customer");

        // Assert that the service throws TrackingException for invalid country code
        TrackingException exception = assertThrows(TrackingException.class, () -> {
            trackingNumberService.generateTrackingNumber(request, UUID.randomUUID().toString());
        });

        assertEquals("Invalid country code", exception.getMessage());
    }

    @Test
    void testGenerateTrackingNumber_invalidWeight() {
        TrackingNumberRequest request = new TrackingNumberRequest(
                "MY", "ID", -1.0, OffsetDateTime.now(), UUID.randomUUID(), "Invalid Customer", "invalid-customer");

        // Assert that the service throws TrackingException for invalid weight
        TrackingException exception = assertThrows(TrackingException.class, () -> {
            trackingNumberService.generateTrackingNumber(request, UUID.randomUUID().toString());
        });

        assertEquals("Invalid weight", exception.getMessage());
    }

    /*@Test
    void testGenerateTrackingNumber_validInput() {
        // Arrange
        TrackingNumberRequest request = new TrackingNumberRequest(
                "MY", "ID", 1.234, OffsetDateTime.now(), UUID.randomUUID(), "Valid Customer", "valid-customer");
        String correlationId = UUID.randomUUID().toString();
        String generatedTrackingNumber = "ABC1234567890123";  // The expected generated tracking number

        // Mock the repository behavior for finding tracking number
        when(trackingNumberRepository.findByTrackingNumber(generatedTrackingNumber)).thenReturn(Optional.empty());

        // Mock the save method (since it's void, we use doNothing)
        doNothing().when(trackingNumberRepository).save(any(TrackingNumberEntity.class));  // Save does nothing

        // Act: Call the service method to generate a tracking number
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(request, correlationId);

        // Assert: Check that the generated tracking number is as expected
        assertEquals(generatedTrackingNumber, response.trackingNumber());

        // Verify that save was called once with the correct argument
        ArgumentCaptor<TrackingNumberEntity> captor = ArgumentCaptor.forClass(TrackingNumberEntity.class);
        verify(trackingNumberRepository, times(1)).save(captor.capture());  // Ensure save was called once

        // Assert that the correct tracking number is saved to the repository
        TrackingNumberEntity savedEntity = captor.getValue();
        assertEquals(generatedTrackingNumber, savedEntity.getTrackingNumber());  // Verify the tracking number
        assertEquals(request.customerId(), savedEntity.getCustomerId());  // Verify the customer ID
    }*/

}
