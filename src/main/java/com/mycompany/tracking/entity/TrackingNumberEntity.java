package com.mycompany.tracking.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tracking_numbers")
public class TrackingNumberEntity {
    @Id
    private String id;
    private String trackingNumber;
    private UUID customerId;
    private String originCountryId;
    private String destinationCountryId;
    private double weight;
    private String createdAt;
    private String correlationId;
}
