package ru.hogwarts.school.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Struct;
import java.util.Collection;
import java.util.Objects;
@Entity
public class Faculty {

    @Id
    @GeneratedValue
    private  long id;
    private String name;
    private String colour;
    @OneToMany(mappedBy = "faculty")
    private Collection<Student> students;

    public Faculty() {
    }

    public Faculty(long id, String name, String colour) {
        this.id = id;
        this.name = name;
        this.colour = colour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Faculty)) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && Objects.equals(name, faculty.name) && Objects.equals(colour, faculty.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, colour);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                '}';
    }
}
