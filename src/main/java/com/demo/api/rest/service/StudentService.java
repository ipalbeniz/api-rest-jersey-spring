package com.demo.api.rest.service;

import com.demo.api.rest.config.spring.CacheCatalog;
import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.Student;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StudentService {

    private static final Map<String, Student> STUDENTS_DATABASE;

    static {
        STUDENTS_DATABASE = new ConcurrentHashMap<>();

        Student student = new Student("1", "Iñaki", 34);

        STUDENTS_DATABASE.put(student.getId(), student);
    }

    public Collection<Student> getStudents() {
        return STUDENTS_DATABASE.values();
    }

    @Cacheable(value = CacheCatalog.STUDENTS_BY_ID_NAME, key="#id")
    public Student getStudentById(String id) {
        return STUDENTS_DATABASE.get(id);
    }

    public void insertStudent(Student student) {
        student.setId(UUID.randomUUID().toString());

        STUDENTS_DATABASE.put(student.getId(), student);
    }

    @CacheEvict(value = CacheCatalog.STUDENTS_BY_ID_NAME, key="#student.id")
    public void updateStudent(Student student) throws AppException {
        if (student.getId() == null) {
            throw new AppException(AppErrorCatalog.ID_REQUIRED_ERROR);
        }

        if (!STUDENTS_DATABASE.containsKey(student.getId())) {
            throw new AppException(AppErrorCatalog.RESOURCE_WITH_ID_NOT_FOUND_ERROR);
        }

        STUDENTS_DATABASE.put(student.getId(), student);
    }
}
