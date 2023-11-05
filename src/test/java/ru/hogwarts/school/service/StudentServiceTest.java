package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepository repository;
    @InjectMocks
    StudentServiceImpl service;
    Student testStudent = new Student(1, "Garry", 14);

    @Test
    void create_shouldAddStudentInDBAndReturnStudent() {
        when(repository.save(testStudent)).thenReturn(testStudent);
        Student result = service.create(testStudent);
        assertEquals(testStudent, result);
    }
    @Test
    void read_shouldReturnStudent() {
        when(repository.findById(testStudent.getId()))
                .thenReturn(Optional.of(testStudent));
        Student result = service.read(testStudent.getId());
        assertEquals(testStudent, result);
    }
    @Test
    void read_shouldThrowExceptionIfStudentNotInDB() {
        when(repository.findById(testStudent.getId()))
                .thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,
                () -> service.read(testStudent.getId()));
    }
    @Test
    void update_shouldReturnUpdatedStudent() {
        when(repository.findById(testStudent.getId()))
                .thenReturn(Optional.of(testStudent));
        testStudent.setAge(11);
        Student result = service.update(testStudent);
        assertEquals(testStudent, result);

    }
    @Test
    void delete_shouldDeleteStudentAndReturnDeletedStudent() {
        when(repository.findById(testStudent.getId()))
                .thenReturn(Optional.of(testStudent));
        Student result = service.delete(testStudent.getId());
        assertEquals(testStudent, testStudent);
    }
    @Test
    void readByAge_shouldReturnCollectionOfAllStudents() {

        when(repository.findAllByAge(testStudent.getAge())).thenReturn();
        Collection<Student> result = service.readByAge(14);
        assertEquals(List.of(testStudent), result);


//        underTest.create(testStudent);
//        underTest.create(testStudent2);
//        underTest.create(testStudent3);
//
//        Collection<Student> result = underTest.readByAge(14);
//        assertEquals(List.of(testStudent, testStudent3), result);
    }
}