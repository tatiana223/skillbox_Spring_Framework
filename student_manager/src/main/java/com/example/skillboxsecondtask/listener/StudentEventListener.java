package com.example.skillboxsecondtask.listener;

import com.example.skillboxsecondtask.event.StudentCreatedEvent;
import com.example.skillboxsecondtask.event.StudentDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventListener {
    private static final Logger log = LoggerFactory.getLogger(StudentEventListener.class);

    @EventListener
    public void handleStudentCreated(StudentCreatedEvent event) {
        log.info("Создан новый студент: {}", event.getStudent().toString());
    }

    @EventListener
    public void handleStudentDeleted(StudentDeletedEvent event) {
        log.info("Удалён студент с ID: {}", event.getStudentId());
    }
}