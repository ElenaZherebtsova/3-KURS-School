package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findAllByColour(String colour);

    Collection<Faculty> findAllByNameIgnoreCaseOrColourIgnoreCase(String name,
                                                                  String colour);
}
