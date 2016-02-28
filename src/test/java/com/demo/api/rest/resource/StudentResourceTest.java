package com.demo.api.rest.resource;

import com.demo.api.rest.model.Student;
import com.demo.api.rest.service.StudentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class StudentResourceTest extends ApiResourceTest {

    @Autowired
    private StudentService studentService;

    @Override
    public Class<? extends ApiResource> getTestedResourceClass() {
        return StudentResource.class;
    }

    @Test
    public void testGetStudentOK() {
        String studentId = "1";

        Response response = jerseyTest.target(StudentResource.STUDENT_RESOURCE_PATH + "/" + studentId).request().get();
        Student studentFromApi = response.readEntity(Student.class);

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(studentService.getStudentById(studentId), studentFromApi);
    }

    @Test
    public void testGetStudentNotFound() {
        String studentId = "9";

        Response response = jerseyTest.target(StudentResource.STUDENT_RESOURCE_PATH + "/" + studentId).request().get();

        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetAllStudents() {
        Response response = jerseyTest.target(StudentResource.STUDENT_RESOURCE_PATH).request().get();
        List<Student> studentsFromApi = response.readEntity(new GenericType<List<Student>>(){});

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(studentService.getStudents(), studentsFromApi);
    }

    @Test
    public void testInsertStudent() {
        Student newStudent = getNewStudent();

        Response response = jerseyTest.target(StudentResource.STUDENT_RESOURCE_PATH).request()
                .post(Entity.entity(newStudent, MediaType.APPLICATION_JSON_TYPE));
        Student studentFromApi = response.readEntity(Student.class);

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertNotNull(studentFromApi.getId());
        Assert.assertEquals(newStudent.getName(), studentFromApi.getName());
    }

    @Test
    public void testInsertInvalidStudent() {
        Student newStudent = getNewInvalidStudent();

        Response response = jerseyTest.target(StudentResource.STUDENT_RESOURCE_PATH).request()
                .post(Entity.entity(newStudent, MediaType.APPLICATION_JSON_TYPE));

        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private Student getNewInvalidStudent() {
        Student student = getNewStudent();

        student.setAge(300);

        return student;
    }

    private Student getNewStudent() {
        Student student = new Student();

        student.setName("Alfredo");
        student.setAge(23);

        return student;
    }
}
