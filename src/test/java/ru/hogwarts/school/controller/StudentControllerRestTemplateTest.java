package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest {

    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    StudentRepository studentRepository;
    String baseUrl;
    Student student = new Student(112L, "Garry", 12);

    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/student";
        studentRepository.deleteAll();
    }

    @Test
    void create_shouldReturnStudentAndStatus200() {
        ResponseEntity<Student> result = restTemplate.postForEntity(baseUrl,
                student,
                Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());

    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl + "/" + student.getId(),
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Студент не найден в хранилище.", result.getBody());
    }

    @Test
    void update_shouldReturnStudentAndStatus200() {
        Student savedStudent = studentRepository.save(student);
        ResponseEntity<Student> result = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(savedStudent),
                Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedStudent, result.getBody());

    }

    @Test
    void delete_shouldReturnStudentAndStatus200() {
        Student savedStudent = studentRepository.save(student);
        ResponseEntity<Student> result = restTemplate
                .exchange(baseUrl + "/" + savedStudent.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(savedStudent),
                Student.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedStudent, result.getBody());
    }

    @Test
    void readByAge_shouldReturnStudentsCollectionAndStatus200() {
        Student savedSt1 = studentRepository.save(student);
        Student ron = new Student(2L, "Ron", 12);
        Student savedSt2 = studentRepository.save(ron);
        ResponseEntity<List<Student>> result = restTemplate
                .exchange(baseUrl + "?age=" + student.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(savedSt1, savedSt2), result.getBody());

    }
}