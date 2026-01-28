package com.example.orderstatusservice;


import com.example.kafkaorder.dto.OrderEvent;
import com.example.kafkaorder.dto.OrderStatusEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class KafkaListenerService {

    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public KafkaListenerService(KafkaTemplate<String, OrderStatusEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-topic", groupId = "status-service-group")
    public void listen(OrderEvent event) {
        OrderStatusEvent statusEvent = new OrderStatusEvent("CREATED", Instant.now());

        kafkaTemplate.send("order-status-topic", statusEvent.getStatus(), statusEvent);
    }
}
