package com.example.orderservice;


import com.example.kafkaorder.dto.OrderStatusEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusListener {

    private static final Logger log = LoggerFactory.getLogger(OrderStatusListener.class);

    @KafkaListener(topics = "order-status-topic", groupId = "order-service-group")
    public void listen(ConsumerRecord<String, OrderStatusEvent> record) {
        log.info("Received message: {}", record.value());
        log.info("Key: {}; Partition: {}; Topic: {}; Timestamp: {}",
                record.key(), record.partition(), record.topic(), record.timestamp());
    }
}
