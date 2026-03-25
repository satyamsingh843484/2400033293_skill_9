package com.experiment.globalexception.exception;

public class StudentNotFoundException extends RuntimeException {

    private final int studentId;

    public StudentNotFoundException(int studentId) {
        super("Student not found with ID: " + studentId);
        this.studentId = studentId;
    }

    public StudentNotFoundException(String message) {
        super(message);
        this.studentId = -1;
    }

    public int getStudentId() {
        return studentId;
    }
}
