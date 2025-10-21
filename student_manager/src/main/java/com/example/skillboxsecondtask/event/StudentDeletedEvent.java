package com.example.skillboxsecondtask.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StudentDeletedEvent extends ApplicationEvent {

    private final Long studentId;

    public StudentDeletedEvent(Object source, Long studentId) {
        super(source);
        this.studentId=studentId;
    }
}
