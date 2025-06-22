package com.mycompany.tracking.repository;

import com.mycompany.tracking.entity.TrackingNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository; --> MongoRepository

import java.util.Optional;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumberEntity, Long> {
    Optional<TrackingNumberEntity> findByTrackingNumber(String trackingNumber);
}
