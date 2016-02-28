package com.demo.api.rest.service.mock;

import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.Student;
import com.demo.api.rest.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StudentServiceMock implements StudentService {

    private static final Map<String, Student> STUDENTS_DATABASE;

    static {
        STUDENTS_DATABASE = new ConcurrentHashMap<>();

        Student student = new Student("1", "Pepe", 26);
        STUDENTS_DATABASE.put(student.getId(), student);

        student = new Student("2", "Juan", 33);
        STUDENTS_DATABASE.put(student.getId(), student);
    }

    @Override
    public Collection<Student> getStudents() {
        return new ArrayList<>(STUDENTS_DATABASE.values());
    }

    @Override
    public Student getStudentById(String id) {
        return STUDENTS_DATABASE.get(id);
    }

    @Override
    public void insertStudent(Student student) {
        student.setId(UUID.randomUUID().toString());

        STUDENTS_DATABASE.put(student.getId(), student);
    }

    @Override
    public void updateStudent(Student student) throws AppException {
        STUDENTS_DATABASE.put(student.getId(), student);
    }

}
