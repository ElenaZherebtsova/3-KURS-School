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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    FacultyRepository facultyRepository;

    String baseUrl;
    Faculty faculty = new Faculty(19L, "Slytherine", "green");

    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/faculty";
        facultyRepository.deleteAll();
    }

    @Test
    void create_shouldReturnFacultyAndStatus200() {
        ResponseEntity<Faculty> result = restTemplate.postForEntity(baseUrl,
                faculty,
                Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty.getName(), result.getBody().getName());
        assertEquals(faculty.getColour(), result.getBody().getColour());
    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(baseUrl + "/" + faculty.getId(),
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Факультет не найден.", result.getBody());
    }

    @Test
    void update_shouldReturnFacultyAndStatus200() {
        Faculty savedFaculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> result = restTemplate.exchange(baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(savedFaculty),
                Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedFaculty, result.getBody());

    }

    @Test
    void delete_shouldReturnFacultyAndStatus200() {
        Faculty savedFaculty = facultyRepository.save(faculty);
        ResponseEntity<Faculty> result = restTemplate
                .exchange(baseUrl + "/" + savedFaculty.getId(),
                        HttpMethod.DELETE,
                        new HttpEntity<>(savedFaculty),
                        Faculty.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(savedFaculty, result.getBody());
    }

    @Test
    void readByColour_shouldReturnFacultyCollectionAndStatus200() {
        Faculty savedFa1 = facultyRepository.save(faculty);
        Faculty hufflepuff = new Faculty(233L, "Sly1", "green");
        Faculty savedFa2 = facultyRepository.save(hufflepuff);
        ResponseEntity<List<Faculty>> result = restTemplate
                .exchange(baseUrl + "?colour=" + faculty.getColour(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(savedFa1, savedFa2), result.getBody());
    }

}