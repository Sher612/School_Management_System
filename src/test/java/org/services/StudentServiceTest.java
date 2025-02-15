package org.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.dao.StudentI;
import org.example.service.StudentService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Student;
import org.example.utility.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertNotNull;

public class StudentServiceTest {
    private StudentService studentService;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp()
    {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        studentService = new StudentService(sessionFactory);
    }

    @AfterEach
    public void tearDown()
    {
        if (transaction != null)
        {
            transaction.rollback();
        }
        if (session != null)
        {
            session.close();
        }
        if (sessionFactory != null)
        {
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateStudent(){
        Student student = new Student();
        student.setName("Sher");
        student.setEmail("sher@gmail.com");
        student.setPassword("dinamadina25$");

        studentService.createStudent(student);
        assertNotNull(student.getEmail());
    }

}
