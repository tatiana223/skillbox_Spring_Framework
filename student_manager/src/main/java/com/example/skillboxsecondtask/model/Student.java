package com.example.skillboxsecondtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return String.format("Студент [ID: %d] %s %s, возраст: %d",
                id, firstName, lastName, age);
    }
}
