package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method to create student.");

        return repository.save(student);
    }

    @Override
    public Student read(long id) {
        logger.info("Was invoked method to read student.");
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент не найден в хранилище."));
    }

    @Override
    public Student update(Student student) {

        logger.info("Was invoked method to update student.");
        read(student.getId());
        return repository.save(student);
    }

    @Override
    public Student delete(long id) {
        logger.info("Was invoked method to delete student.");
        Student readedStudent = read(id);
        repository.delete(readedStudent);
        return readedStudent;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        logger.info("Was invoked method to find students by age.");
        return repository.findAllByAge(age);
    }

    @Override
    public Collection<Student> readByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method to find students between min-age and max-age.");
        return repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty readFacultyOfStudent(long idStudent) {

        logger.info("Was invoked method to find faculty of student.");
        return read(idStudent).getFaculty();
    }

    @Override
    public Collection<Student> readByFacultyId(long idFaculty) {

        logger.info("Was invoked method to find all students by faculty.");
        return repository.findAllByFaculty_id(idFaculty);
    }

    @Override
    // 4.5.1. Сортировка студентов по имени на букву А
    public Collection<String> getFilteredByNameA() {
        return (Collection<String>) repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s ->s.startsWith("A"))
                .sorted()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    // 4.5.2. Средний возраст студентов
    public Double getStudentAvgAge () {
        return repository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    //4.6.1. Вывод имен студентов в разных потоках
    public void getStudentNamesInThreads() {
        Thread  thread1 = new Thread(()->{
            printName(3L);
            printName(4L);
        });
        thread1.setName("Thread #1");

        Thread  thread2 = new Thread(()->{
            printName(5L);
            printName(6L);
        });
        thread2.setName("Thread #2");

        thread1.start();
        thread2.start();

        printName(1L);
        printName(2L);
    }

    private void printName(long id) {
        String studentName = repository.getById(id).getName();
        System.out.println(studentName);
    }

    @Override
    //4.6.2. Вывод имен студентов в синхронном режиме.
    public void getStudentNamesSync () {
        Thread thread1= new Thread(()->{
            printNameSync(3L);
            printNameSync(4L);
        });
        Thread thread2= new Thread(()->{
            printNameSync(5L);
            printNameSync(6L);
        });

        printNameSync(1L);
        printNameSync(2L);
        thread1.start();
        thread2.start();

    }

    private synchronized void printNameSync(Long id) {
        String studentName = repository.getById(id).getName();
        System.out.println(studentName);
    }
}
