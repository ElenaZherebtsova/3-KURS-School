package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistException;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException("Факультет не найден."));
    }

    @Override
    public Faculty update(Faculty faculty) {
        read(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        Faculty readedFaculty = read(id);
        repository.delete(readedFaculty);
        return readedFaculty;
    }

    @Override
    public Collection<Faculty> readByColour(String colour) {
        return repository.findAllByColour(colour);
    }
}
