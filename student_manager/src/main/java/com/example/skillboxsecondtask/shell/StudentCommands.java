package com.example.skillboxsecondtask.shell;

import com.example.skillboxsecondtask.model.Student;
import com.example.skillboxsecondtask.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class StudentCommands {

    private final StudentService studentService;

    @ShellMethod(key = "students", value = "Показать всех студентов")
    public String showAllStudents() {
        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            return "Список студентов пуст";
        }

        StringBuilder sb = new StringBuilder("Список студентов:\n");
        for (int i = 0; i < students.size(); i++) {
            sb.append(i + 1).append(". ").append(students.get(i)).append("\n");

        }
        return sb.toString();

    }

    @ShellMethod(key = "add-student", value = "Добавить нового студента")
    public String addStudent(
            @ShellOption(value = {"-f", "firstname"}) String firstName,
            @ShellOption(value = {"-l", "--lastname"}) String lastName,
            @ShellOption(value = {"-a", "--age"}) Integer age) {

        if (age <= 0) {
            return "Ошибка: возраст должен быть положительным числом";
        }

        Student student = studentService.createStudent(firstName, lastName, age);
        return String.format("Студент добавлен: %s", student);

    }

    @ShellMethod(key = "delete-student", value = "Удалить студента по ID")
    public String deleteStudent(@ShellOption Long id) {
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return String.format("Студент с ID %d удален", id);
        } else {
            return String.format("Студент с ID %d не найден", id);
        }
    }

    @ShellMethod(key = "clear-students", value = "Очистить список студентов")
    public String clearAllStudents() {
        studentService.clearAllStudents();
        return "Все студенты удалены";
    }

    @ShellMethod(key = "find-student", value = "Найти студента по ID")
    public String findStudentById(@ShellOption Long id) {
        Student student = studentService.findStudentId(id);
        if (student != null) {
            return student.toString();
        } else {
            return String.format("Студент с ID %d не найден", id);
        }
    }

}
