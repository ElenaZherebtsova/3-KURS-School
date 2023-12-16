package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {

        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {

        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {

        return facultyService.read(id);
    }

    @PutMapping
    public Faculty update(
            @RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping
    public Collection<Faculty> readByColour(@RequestParam String colour) {

        return facultyService.readByColour(colour);
    }

    @GetMapping("/nameOrColour")
    public Collection<Faculty> readByNameOrColour(@RequestParam String name,
                                                  @RequestParam String colour) {
        return facultyService.readByNameorColour(name, colour);
    }

    @GetMapping("/maxfacultyname")
    //4.5.3.Получение факультета с МАХ длиной имени
    public ResponseEntity<String> getFacultyWithMaxnameLength() {
        return facultyService.getFacultyMaxLengthName();
    }



}
