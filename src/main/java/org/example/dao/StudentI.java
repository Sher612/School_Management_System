package org.example.dao;

import org.example.model.Course;
import org.example.model.Student;
import java.util.List;

/**
 * The StudentI interface declares abstract methods and
 * is implemented by other classes to provide services for a student.
 */
public interface StudentI {
    List<Student> getAllStudents();
    void createStudent(Student student);

    Student getStudentByEmail(String email);

    boolean validateStudent(String email, String password);

    void registerStudentToCourse(String email, int courseId);

    List<Course> getStudentCourses(String email);
}



