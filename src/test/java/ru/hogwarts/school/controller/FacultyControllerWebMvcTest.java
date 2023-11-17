package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @MockBean
    FacultyRepository facultyRepository;
    @SpyBean
    FacultyServiceImpl facultyService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    Faculty faculty = new Faculty(1L, "Gryffindor", "red");


    @Test
    void create_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void read_shouldReturnStatus404() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Факультет не найден."));
    }

    @Test
    void update_shouldReturnUpdatedFacultyAndStatus200() throws Exception {
        facultyRepository.save(faculty);
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        faculty.setColour("green");
        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void delete_shouldReturnStatus404() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());
        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Факультет не найден."));
    }
    @Test
    void readByColour_shouldReturnFacultiesCollectionAndStatus200() throws Exception {
        Faculty faculty1 = new Faculty(2L, "Gryffindor1", "red");
        when(facultyRepository.findAllByColour(faculty.getColour()))
                .thenReturn(List.of(faculty, faculty1));
        mockMvc.perform(get("/faculty?colour=" + faculty.getColour()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(faculty))
                .andExpect(jsonPath("$[1]").value(faculty1));

    }
}