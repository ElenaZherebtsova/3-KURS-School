package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService underTest = new StudentServiceImpl();
    private Student testStudent = new Student(1, "Garry", 14);

    @Test
    void create_shouldAddStudentInMapAndReturnStudent() {
        Student result = underTest.create(testStudent);
        assertTrue(underTest.readByAge(14).contains(testStudent));
        assertEquals(testStudent, result);
    }
    @Test
    void create_shouldThrowExceptionIfStudentAlreadyInMap() {
        Student result = underTest.create(testStudent);
        assertThrows(StudentAlreadyExistException.class,
                () -> underTest.create(testStudent));
    }

    @Test
    void read_shouldReturnStudentIfStudentInMap() {
        underTest.create(testStudent);
        Student result = underTest.read(testStudent.getId());
        assertTrue(underTest.readByAge(14).contains(testStudent));
        assertEquals(testStudent, result);
    }
    @Test
    void read_shouldThrowExceptionIfStudentNotInMap() {
        assertThrows(StudentNotFoundException.class,
                () -> underTest.read(testStudent.getId()));

    }



    @Test
    void update_shouldReturnUpdatedStudent() {
        underTest.create(testStudent);
        testStudent.setAge(17);
        Student result = underTest.update(testStudent);
        assertTrue(underTest.readByAge(17).contains(testStudent));
        assertEquals(testStudent, result);
    }

       @Test
    void update_shouldThrowExceptionIfStudentNotInMap() {
           assertThrows(StudentNotFoundException.class,
                   () -> underTest.update(testStudent));
    }


    @Test
    void delete_shouldDeleteStudentAndReturnDeletedStudent() {
        underTest.create(testStudent);
        Student result = underTest.delete(testStudent.getId());
        assertFalse(underTest.readByAge(14).contains(testStudent));
        assertEquals(testStudent, result);
    }

    @Test
    void delete_shouldReturnExceptionIfStudentNotFound() {
        assertThrows(StudentNotFoundException.class,
                () -> underTest.delete(testStudent.getId()));
    }

    @Test
    void readByAge_shouldReturnCollectionOfAllStudents() {
        Student testStudent2 = new Student(2, "Ron", 15);
        Student testStudent3 = new Student(3, "Germiona", 14);
        underTest.create(testStudent);
        underTest.create(testStudent2);
        underTest.create(testStudent3);

        Collection<Student> result = underTest.readByAge(14);
        assertEquals(List.of(testStudent, testStudent3), result);
    }
}