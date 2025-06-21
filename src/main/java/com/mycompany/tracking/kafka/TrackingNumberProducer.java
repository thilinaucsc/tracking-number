package com.mycompany.tracking.kafka;

import com.mycompany.tracking.dto.TrackingNumberResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackingNumberProducer {

    private final KafkaTemplate<String, TrackingNumberResponse> kafkaTemplate;

    @Autowired
    public TrackingNumberProducer(KafkaTemplate<String, TrackingNumberResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTrackingNumberEvent(TrackingNumberResponse trackingNumberResponse) {
        kafkaTemplate.send("tracking-number-topic", trackingNumberResponse);
    }
}