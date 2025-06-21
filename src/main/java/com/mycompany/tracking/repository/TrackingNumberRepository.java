package com.mycompany.tracking.repository;

import com.mycompany.tracking.entity.TrackingNumberEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TrackingNumberRepository extends MongoRepository<TrackingNumberEntity, String> {
    Optional<TrackingNumberEntity> findByTrackingNumber(String trackingNumber);
}
