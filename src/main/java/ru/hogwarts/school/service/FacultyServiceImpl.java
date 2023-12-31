package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method to create faculty.");
        return repository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        logger.info("Was invoked method to read faculty.");
        return repository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException("Факультет не найден."));
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method to update faculty.");
        read(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        logger.info("Was invoked method to delete faculty.");
        Faculty readedFaculty = read(id);
        repository.delete(readedFaculty);
        return readedFaculty;
    }

    @Override
    public Collection<Faculty> readByColour(String colour) {
        logger.info("Was invoked method to read faculties by colour.");

        return repository.findAllByColour(colour);
    }

    @Override
    public Collection<Faculty> readByNameorColour(String name,
                                                  String colour) {
        logger.info("Was invoked method to find faculty by colour or by name.");
        return repository.findAllByNameIgnoreCaseOrColourIgnoreCase(name, colour);
    }

}
