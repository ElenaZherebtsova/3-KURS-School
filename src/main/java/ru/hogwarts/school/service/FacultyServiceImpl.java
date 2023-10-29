package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyAlreadyExistException;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> repository = new HashMap<>();
    private long countID = 0L;

    @Override
    public Faculty create(Faculty faculty) {
        if (repository.containsValue(faculty)) {
            throw new FacultyAlreadyExistException("Такой факультет уже добавлен.");
        }

        long id = ++countID;
        faculty.setId(id);
         repository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty read(long id ) {
        Faculty faculty = repository.get(id);

        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет не найден.");
        }
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!repository.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException("Факультет не найден.");
        }

        repository.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty delete(long id) {
        Faculty removedFaculty = repository.remove(id);
        if (removedFaculty == null) {
            throw new FacultyNotFoundException("Факультет с id = " + id + " не найден.");
                    }
        return removedFaculty;
    }

    @Override
    public Collection<Faculty> readByColour(String colour) {
         return repository.values().stream()
                .filter(faculty -> faculty.getColour() == colour)
                .collect(Collectors.toUnmodifiableList());
    }
}
