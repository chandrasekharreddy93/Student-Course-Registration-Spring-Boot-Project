package com.example.student.service;

import com.example.student.model.Student;
import com.example.student.exception.StudentNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StudentService {

    // This ArrayList stores all registered students.
    private final ArrayList<Student> students = new ArrayList<>();

    // Student ID starts from 1.
    private long nextId = 1L;

    // 1. Register a new student
    public synchronized Student registerStudent(Student request) {

        // Create a new student and generate ID automatically.
        Student student = new Student(
                nextId++,
                request.getStudentName().trim(),
                request.getCourseName().trim(),
                request.getEmail().trim(),
                request.getAge()
        );

        // Store student details in ArrayList.
        students.add(student);

        // Return a copy of the registered student.
        return copyStudent(student);
    }

    // 2. View all registered students
    public synchronized List<Student> getAllStudents() {

        return students.stream()
                .map(this::copyStudent)
                .toList();
    }

    // 3. Find student using ID
    public synchronized Student getStudentById(Long id) {

        Student student = students.stream()
                .filter(item -> item.getStudentId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: " + id
                        )
                );

        return copyStudent(student);
    }

    // 4. Delete student registration
    public synchronized String deleteStudent(Long id) {

        Student student = students.stream()
                .filter(item -> item.getStudentId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: " + id
                        )
                );

        students.remove(student);

        return "Student registration deleted successfully. ID: " + id;
    }

    // 5. Custom query: Search students by name
    public synchronized List<Student> searchByName(String keyword) {

        checkNotBlank(keyword, "Search name cannot be empty");

        String searchText = keyword.trim().toLowerCase(Locale.ROOT);

        return students.stream()
                .filter(student ->
                        student.getStudentName()
                                .toLowerCase(Locale.ROOT)
                                .contains(searchText)
                )
                .map(this::copyStudent)
                .toList();
    }

    // 6. Custom query: Search students by course
    public synchronized List<Student> searchByCourse(String courseName) {

        checkNotBlank(courseName, "Course name cannot be empty");

        return students.stream()
                .filter(student ->
                        student.getCourseName()
                                .equalsIgnoreCase(courseName.trim())
                )
                .map(this::copyStudent)
                .toList();
    }

    // 7. Custom query: Find students by minimum age
    public synchronized List<Student> searchByAge(int minAge) {

        if (minAge < 18) {
            throw new IllegalArgumentException(
                    "Minimum age must be at least 18"
            );
        }

        return students.stream()
                .filter(student -> student.getAge() >= minAge)
                .map(this::copyStudent)
                .toList();
    }

    // 8. Custom query: Search student by email
    public synchronized Student searchByEmail(String email) {

        checkNotBlank(email, "Email cannot be empty");

        return students.stream()
                .filter(student ->
                        student.getEmail()
                                .equalsIgnoreCase(email.trim())
                )
                .findFirst()
                .map(this::copyStudent)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with email: " + email
                        )
                );
    }

    // 9. Custom query: Find students within a given age range
    public synchronized List<Student> searchByAgeRange(
            int minAge, int maxAge) {

        if (minAge < 18 || maxAge < minAge) {
            throw new IllegalArgumentException(
                    "Invalid age range. Minimum age must be 18 and maximum age must be greater than minimum age"
            );
        }

        return students.stream()
                .filter(student ->
                        student.getAge() >= minAge &&
                        student.getAge() <= maxAge
                )
                .map(this::copyStudent)
                .toList();
    }

    // 10. Custom query: Find students by course name and minimum age
    public synchronized List<Student> searchByCourseAndAge(
            String courseName, int minAge) {

        checkNotBlank(courseName, "Course name cannot be empty");

        if (minAge < 18) {
            throw new IllegalArgumentException(
                    "Minimum age must be at least 18"
            );
        }

        return students.stream()
                .filter(student ->
                        student.getCourseName()
                                .equalsIgnoreCase(courseName.trim())
                )
                .filter(student -> student.getAge() >= minAge)
                .map(this::copyStudent)
                .toList();
    }

    // 11. Update existing student details
    public synchronized Student updateStudent(Long id, Student request) {

        // Find the student using ID.
        Student existingStudent = students.stream()
                .filter(student ->
                        student.getStudentId().equals(id)
                )
                .findFirst()
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student not found with ID: " + id
                        )
                );

        // Update the existing student details.
        existingStudent.setStudentName(
                request.getStudentName().trim()
        );

        existingStudent.setCourseName(
                request.getCourseName().trim()
        );

        existingStudent.setEmail(
                request.getEmail().trim()
        );

        existingStudent.setAge(
                request.getAge()
        );

        // Return updated student details.
        return copyStudent(existingStudent);
    }

    // Check whether the search input is empty.
    private void checkNotBlank(String value, String message) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    // Create a separate copy of student details.
    private Student copyStudent(Student student) {

        return new Student(
                student.getStudentId(),
                student.getStudentName(),
                student.getCourseName(),
                student.getEmail(),
                student.getAge()
        );
    }
}