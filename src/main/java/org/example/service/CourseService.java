package org.example.service;

import org.example.dao.CourseI;
import org.example.model.Course;
import org.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseI {
    private final SessionFactory sessionFactory;

    public CourseService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createCourse(Course course)
    {
        try (Session session = this.sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            session.persist(course);
            transaction.commit();
            System.out.println("Course " + course.getId() + " has been created");
        } catch (Exception e)
        {
           e.printStackTrace();
        }
    }

    @Override
    public Course getCourseById(int courseId)
    {
        Course course = null;

        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Course c WHERE id = :id";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("id", courseId);
            course = query.getSingleResult();
            transaction.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses()
    {
        List<Course> courses = new ArrayList<>();

        try (Session session = sessionFactory.openSession())
        {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Course";
            Query<Course> query = session.createQuery(hql, Course.class);
            courses = query.getResultList();
            transaction.commit();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return courses;
    }
}

    
