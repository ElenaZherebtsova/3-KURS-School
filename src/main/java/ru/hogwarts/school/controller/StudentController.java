package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {

        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {

        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {

        return studentService.delete(id);
    }

    @GetMapping
    public Collection<Student> readByAge(@RequestParam int age) {
        return studentService.readByAge(age);
    }

    @GetMapping("/age")
    public Collection<Student> readByAgeBetween(@RequestParam int minAge,
                                                @RequestParam int maxAge) {
        return studentService.readByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/faculty")
    public Faculty readFacultyOfStudent(@RequestParam long idStudent) {
        return studentService.readFacultyOfStudent(idStudent);
    }

    @GetMapping("/readFaculty")
    public Collection<Student> readByFaculty(@RequestParam long idFaculty) {
        return studentService.readByFacultyId(idFaculty);
    }


    // 4.5.1. Сортировка студентов по имени на букву А
    @GetMapping("/filteredbynamea")
    public ResponseEntity<Collection<String>> getAllStudentsWithNameA() {
        Collection<String> studentSpisok = studentService.getFilteredByNameA();
        if (studentSpisok.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(studentSpisok);
    }

    @GetMapping("/getallstudentsaverageage")
    // 4.5.2. Средний возраст студентов
    public Double getAllStudentsAvgAge () {

        return studentService.getStudentAvgAge();
    }

    @GetMapping("/print-parallel")
    //4.6.1. Вывод имен студентов в параллельных потоках
    public void getNames () throws InterruptedException {
        studentService.getStudentNamesInThreads();
    }

    @GetMapping("/print-synchronized")
    //4.6.2. Вывод имен студентов в синхронном режиме
    public void getNamesSync () throws InterruptedException {
        studentService.getStudentNamesSync();

    }
}
