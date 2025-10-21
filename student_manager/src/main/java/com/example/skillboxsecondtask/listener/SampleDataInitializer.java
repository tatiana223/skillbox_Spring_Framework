package com.example.skillboxsecondtask.listener;

import com.example.skillboxsecondtask.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.init-sample-data", havingValue = "true")
public class SampleDataInitializer {
    private static final Logger log = LoggerFactory.getLogger(SampleDataInitializer.class);
    private final StudentService studentService;

    @EventListener(ApplicationReadyEvent.class)

    public void initSampleData() {
        log.info("Инициализация тестовых данных...");
        studentService.createStudent("Иван", "Иванов", 20);
        studentService.createStudent("Петр", "Петров", 21);
        studentService.createStudent("Мария", "Сидорова", 19);
        studentService.createStudent("Анна", "Кузнецова", 22);

        log.info("Тестовые данные созданы");
    }

}
