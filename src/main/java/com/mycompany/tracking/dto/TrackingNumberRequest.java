package com.mycompany.tracking.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.UUID;

public record TrackingNumberRequest (
    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$", message = "Origin country ID must be ISO 3166-1 alpha-2")
    String originCountryId,

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$", message = "Destination country ID must be ISO 3166-1 alpha-2")
    String destinationCountryId,

    @NotNull
    @DecimalMin(value = "0.001", message = "Weight must be at least 0.001 kg")
    @Digits(integer = 5, fraction = 3, message = "Weight must have up to 3 decimal places")
//    @DecimalMin(value = "0.001", inclusive = true)
//    @Digits(integer = 6, fraction = 3)
    double weight,

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    OffsetDateTime createdAt,

    @NotNull
    UUID customerId,

    @NotBlank
    String customerName,

    @NotBlank
    String customerSlug
) {}
