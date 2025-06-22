package com.mycompany.tracking.controller;

import com.mycompany.tracking.dto.TrackingNumberRequest;
import com.mycompany.tracking.service.TrackingNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrackingNumberController.class)  // Only test the controller layer
public class TrackingNumberControllerTests {

    @Autowired
    private MockMvc mockMvc;  // Injected by Spring

    @MockBean
    private TrackingNumberService trackingNumberService;  // Mocked service bean

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // MockMvc is automatically injected and set up by @WebMvcTest
    }

//    @Test
//    void shouldReturnTrackingNumber() throws Exception {
//
//        String originCountryId = "MY";
//        String destinationCountryId = "ID";
//        double weight = 1.234;
//        String correlationId = UUID.randomUUID().toString();
//        String generatedTrackingNumber = "ABC1234567890123";
//        String customerName = "RedBox Logistics";
//        String customerSlug = "redbox-logistics";
//
//        TrackingNumberRequest request = new TrackingNumberRequest(
//                originCountryId, destinationCountryId, weight, OffsetDateTime.now(), UUID.randomUUID(), customerName, customerSlug);
//
//        // Mock the service response
//        when(trackingNumberService.generateTrackingNumber(request, correlationId))
//                .thenReturn(new TrackingNumberResponse(generatedTrackingNumber, OffsetDateTime.now()));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/next-tracking-number")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(request))  // Convert the request object to JSON
//                        .header("X-Correlation-ID", correlationId))
//                .andDo(print())// Set the X-Correlation-ID header
//                .andExpect(status().isOk())  // Expect a 200 OK response
//                .andExpect(jsonPath("$.data.trackingNumber").value(generatedTrackingNumber))  // Use camel case for trackingNumber
//                .andExpect(jsonPath("$.data.correlationId").value(correlationId));   // Check correlation ID in response
//    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        // Arrange
        TrackingNumberRequest invalidRequest = new TrackingNumberRequest(
                "INVALID", "ID", 1.234, OffsetDateTime.now(), UUID.randomUUID(), "Invalid Customer", "invalid-customer");

        // Act & Assert
        mockMvc.perform(post("/api/next-tracking-number")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest))  // Convert to JSON
                        .header("X-Correlation-ID", UUID.randomUUID().toString()))  // Set a random correlation ID
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request response
    }
}
