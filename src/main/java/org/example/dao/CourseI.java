package org.example.dao;

import org.example.model.Course;
import org.example.model.Student;

import java.util.List;

/**
 * The CourseI interface declares abstract methods and
 * is implemented by other classes to provide services for a course.
 */
public interface CourseI {
    void createCourse(Course course);
    Course getCourseById(int courseId);
    List<Course> getAllCourses();

}
