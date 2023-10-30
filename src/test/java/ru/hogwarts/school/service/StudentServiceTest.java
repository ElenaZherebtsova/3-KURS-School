//package ru.hogwarts.school.service;
//
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Student;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StudentServiceTest {
//    private StudentService underTest = new StudentService();
//    private Student testStudent = new Student(1, "Garry", 14);
//
//    @Test
//    void create_shouldAddStudentInMapAndReturnStudent() {
//        Student result = underTest.create(testStudent.getId(), testStudent.getName(), testStudent.getAge());
//        assertTrue(underTest.readByAge(14).contains(testStudent));
//        assertEquals(testStudent, result);
//    }
//
//    @Test
//    void read() {
//    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void readByAge() {
//    }
//}