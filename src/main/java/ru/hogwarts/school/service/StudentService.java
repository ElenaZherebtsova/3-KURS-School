package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    Student delete(long id);

    Collection<Student> readByAge(int age);

    Collection<Student> readByAgeBetween(int minAge, int maxAge);

    Faculty readFacultyOfStudent(long idStudent);

    Collection<Student> readByFacultyId(long idFaculty);
}
