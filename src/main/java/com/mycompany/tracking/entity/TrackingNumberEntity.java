package com.mycompany.tracking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "tracking_numbers") // --> mongo
@Entity
@Table(name = "tracking_numbers")
public class TrackingNumberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String trackingNumber;
    private UUID customerId;
    private String originCountryId;
    private String destinationCountryId;
    private double weight;
    private String createdAt;
    private String correlationId;
}
