package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentAlreadyExistException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {


    private final Map<Long, Student> repository = new HashMap<>();
    private long countID = 0L;


    @Override
    public Student create(Student student) {
        if (repository.containsValue(student)) {
            throw new StudentAlreadyExistException("Студент уже был добавлен.");
        }

        long id = ++countID;
        student.setId(id);

                repository.put(id, student);
        return student;
    }

    @Override
    public Student read(long id) {
        Student student = repository.get(id);

        if (student == null) {
            throw new StudentNotFoundException("Студент не найден в хранилище.");

        }

        return student;
    }

    @Override
    public Student update(Student student) {
        if (!repository.containsKey(student.getId())) {
            throw new StudentNotFoundException("Студент с таким id не найден.");
        }
         repository.put(student.getId(), student);

        return student;
    }

    @Override
    public Student delete(long id) {
        Student removedStudent = repository.remove(id);
        if (removedStudent == null) {
            throw new StudentNotFoundException("Студент не найден в хранилище.");

        }

        return removedStudent;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        return repository.values().stream()
                .filter(student -> student.getAge()==age)
                .collect(Collectors.toUnmodifiableList());

    }

}
