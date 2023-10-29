package ru.hogwarts.school.exceptions;

public class FacultyAlreadyExistException extends RuntimeException{
    public FacultyAlreadyExistException(String message) {
        super(message);
    }
}
