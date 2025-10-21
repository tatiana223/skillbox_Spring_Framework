package com.example.skillboxsecondtask.service;

import com.example.skillboxsecondtask.event.StudentCreatedEvent;
import com.example.skillboxsecondtask.event.StudentDeletedEvent;
import com.example.skillboxsecondtask.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ApplicationEventPublisher eventPublisher;

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student createStudent(String firstName, String lastName, int age) {
        Long id = idGenerator.getAndIncrement();
        Student student = new Student(id, firstName, lastName, age);

        students.add(student);

        eventPublisher.publishEvent(new StudentCreatedEvent(this, student));
        return student;

    }

    public boolean deleteStudent(Long id) {
        boolean removed = students.removeIf(student -> student.getId().equals(id));
        if (removed) {
            eventPublisher.publishEvent(new StudentDeletedEvent(this, id));
        }

        return removed;
    }

    public void clearAllStudents() {
        students.forEach(student -> eventPublisher.publishEvent(new StudentDeletedEvent(this, student.getId())));
        students.clear();
    }

    public Student findStudentId(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
