package org.example.model;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name= "student")
@Getter
@Setter
@ToString(exclude = "courses")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor

public class Student {

    @Id
    @Column (name = "email", length = 50)
    private final String email;

    @Column(name = "name", length = 50, nullable = false)
    private final String name;

    @Column(name = "password", length = 50, nullable = false)
    private final String password;

    @ManyToMany (cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name= "student_courses",
            joinColumns = @JoinColumn(name = "student_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id")
    )
    private Set<Course> courses = new LinkedHashSet<>();

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    //Override Equals and Hashcode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) && Objects.equals(name, student.name) && Objects.equals(password, student.password) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, password, courses);
    }

}
