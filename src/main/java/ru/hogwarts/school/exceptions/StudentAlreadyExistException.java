package ru.hogwarts.school.exceptions;

public class StudentAlreadyExistException extends RuntimeException{
    public StudentAlreadyExistException(String message) {
        super(message);
    }
}
