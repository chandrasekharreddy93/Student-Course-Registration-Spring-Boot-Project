package com.example.student.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    // Student ID is generated automatically.
    private Long studentId;

    // Student name should not be empty.
    @NotBlank(message = "Student name cannot be empty")
    private String studentName;

    // Course name is required.
    @NotBlank(message = "Course name cannot be empty")
    private String courseName;

    // Email should be in a valid format.
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter valid email")
    private String email;

    // Student must be at least 18 years old.
    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    private Integer age;

}