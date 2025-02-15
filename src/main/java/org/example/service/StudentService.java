package org.example.service;

import org.example.dao.StudentI;
import org.example.model.Course;
import org.example.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class StudentService implements StudentI{
    private final SessionFactory sessionFactory;

    public StudentService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override

    public List<Student> getAllStudents() {
        Transaction tx = null;
        List<Student> students = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query<Student> query = session.createQuery("from Student", Student.class);
            students = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return students;
    }

    @Override

    public void createStudent(Student student) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession())
        {
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
            System.out.println("student successfully created: " + student.getEmail());
        }catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            System.out.println("student could not be created: " + student.getEmail());
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Transaction tx = null;
        Student student = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query <Student> query = session.createQuery("from Student s WHERE s.email = :email", Student.class);
            query.setParameter("email", email);
            student = query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Transaction tx = null;
        boolean isValid = false;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query <Student> query = session.createQuery("from Student where email = :email and password = :password", Student.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            isValid = query.uniqueResult() != null;
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return isValid;
    }


    @Override
    public void registerStudentToCourse(String email, int courseId) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Query<Student> query = session.createQuery("from Student where email = :email", Student.class);
            query.setParameter("email", email);
            Student student = (Student) query.uniqueResult();
            Course course = session.get(Course.class, courseId);
            if (student != null & course != null) {
                if (student.getCourses()== null) {
                    student.setCourses(new LinkedHashSet<>());
                }
                student.getCourses().add(course);
                session.update(student);
            } else {
                System.out.println("Neither Student Nor Course Found");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        List<Course> courses = new ArrayList<>();
        Transaction tx = null;
        try (Session session = sessionFactory.openSession())
        {
            tx = session.beginTransaction();
            //HQL query to fetch courses based on student email
            String hql = "SELECT c FROM Course c JOIN c.students s WHERE s.email = :email";
            Query<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("email", email);
            courses = query.getResultList();
            tx.commit();
        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return courses;
    }
}

