package com.demo.api.rest.service;


import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getStudents();

    Student getStudentById(String id);

    void insertStudent(Student student);

    void updateStudent(Student student) throws AppException;
}
