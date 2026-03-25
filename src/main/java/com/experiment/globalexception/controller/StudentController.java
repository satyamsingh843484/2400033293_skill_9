package com.experiment.globalexception.controller;

import com.experiment.globalexception.exception.InvalidInputException;
import com.experiment.globalexception.exception.StudentNotFoundException;
import com.experiment.globalexception.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * StudentController - REST API for student operations.
 * Demonstrates exception throwing for global handling.
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    // Simulated in-memory student database
    private static final List<Student> studentDatabase = new ArrayList<>();

    static {
        studentDatabase.add(new Student(1, "Alice Johnson", "alice@example.com", 20));
        studentDatabase.add(new Student(2, "Bob Smith",    "bob@example.com",   22));
        studentDatabase.add(new Student(3, "Carol White",  "carol@example.com", 21));
    }

    /**
     * GET /api/student/{id}
     * Retrieves a student by ID. Throws StudentNotFoundException if not found.
     */
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {

        // Validate: ID must be positive
        if (id <= 0) {
            throw new InvalidInputException("id", id, "Student ID must be a positive integer.");
        }

        Student student = studentDatabase.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));

        return ResponseEntity.ok(student);
    }

    /**
     * GET /api/students
     * Returns all students.
     */
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentDatabase);
    }

    /**
     * POST /api/student
     * Adds a new student with input validation.
     */
    @PostMapping("/student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidInputException("name", student.getName(), "Student name cannot be null or empty.");
        }

        if (student.getAge() < 1 || student.getAge() > 100) {
            throw new InvalidInputException("age", student.getAge(),
                    "Age must be between 1 and 100. Received: " + student.getAge());
        }

        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            throw new InvalidInputException("email", student.getEmail(),
                    "Invalid email address: " + student.getEmail());
        }

        // Auto-assign ID
        int newId = studentDatabase.size() + 1;
        student.setId(newId);
        studentDatabase.add(student);

        return ResponseEntity.status(201).body(student);
    }

    /**
     * DELETE /api/student/{id}
     * Deletes a student by ID.
     */
    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {

        if (id <= 0) {
            throw new InvalidInputException("id", id, "Student ID must be a positive integer.");
        }

        boolean removed = studentDatabase.removeIf(s -> s.getId() == id);
        if (!removed) {
            throw new StudentNotFoundException(id);
        }

        return ResponseEntity.ok("Student with ID " + id + " deleted successfully.");
    }
}
