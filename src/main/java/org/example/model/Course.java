package org.example.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@ToString(exclude = "students")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private int id;

    @Column (name = "name", length = 50, nullable = false)
    private final String name;

    @Column (name = "instructor", length = 50, nullable = false)
    private final String instructor;

    @ManyToMany (mappedBy = "courses", fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Student> students = new LinkedHashSet<>();

    public void addStudent(Student student) {
        this.students.add(student);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor, students);
    }

}
