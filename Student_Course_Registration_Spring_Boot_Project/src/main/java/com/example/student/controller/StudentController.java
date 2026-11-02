package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    // Constructor injection
    public StudentController(StudentService service) {

        this.service = service;

    }

    // 1. Register a new student
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student registerStudent(
            @Valid @RequestBody Student student) {

        return service.registerStudent(student);
    }

    // 2. View all students
    @GetMapping
    public List<Student> getAllStudents() {

        return service.getAllStudents();
    }

    // 3. Find student by ID
    @GetMapping("/{id}")
    public Student getStudentById(
            @PathVariable("id") Long id) {

        return service.getStudentById(id);
    }

    // 4. Delete student registration
    @DeleteMapping("/{id}")
    public String deleteStudent(
            @PathVariable("id") Long id) {

        return service.deleteStudent(id);
    }

    // 5. Search students by name
    @GetMapping("/search")
    public List<Student> searchByName(
            @RequestParam("keyword") String keyword) {

        return service.searchByName(keyword);
    }

    // 6. Search students by course
    @GetMapping("/course")
    public List<Student> searchByCourse(
            @RequestParam("name") String courseName) {

        return service.searchByCourse(courseName);
    }

    // 7. Search students by minimum age
    @GetMapping("/age")
    public List<Student> searchByAge(
            @RequestParam("minAge") int minAge) {

        return service.searchByAge(minAge);
    }

    // 8. Search student by email
    @GetMapping("/email")
    public Student searchByEmail(
            @RequestParam("email") String email) {

        return service.searchByEmail(email);
    }

    // 9. Search students within an age range
    @GetMapping("/age-range")
    public List<Student> searchByAgeRange(
            @RequestParam("minAge") int minAge,
            @RequestParam("maxAge") int maxAge) {

        return service.searchByAgeRange(minAge, maxAge);
    }

    // 10. Search students by course and minimum age
    @GetMapping("/course-age")
    public List<Student> searchByCourseAndAge(
            @RequestParam("course") String courseName,
            @RequestParam("minAge") int minAge) {

        return service.searchByCourseAndAge(courseName, minAge);
    }

    // 11. Update student details
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable("id") Long id,
            @Valid @RequestBody Student student) {

        return service.updateStudent(id, student);
    }
}
