package com.demo.api.rest.resource;

import com.demo.api.rest.config.jersey.annotations.CacheControlMaxAge;
import com.demo.api.rest.config.jersey.annotations.CacheControlNoCache;
import com.demo.api.rest.config.jersey.annotations.GZip;
import com.demo.api.rest.config.jersey.annotations.Log;
import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.Student;
import com.demo.api.rest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
@Path("/student")
public class StudentResource {

    @Autowired
    private StudentService studentService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @GZip
    @Log
    public Response getStudents() {

        Collection<Student> students = studentService.getStudents();

        return Response.status(Response.Status.OK)
                .entity(students)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @CacheControlMaxAge(time = 60, unit = TimeUnit.SECONDS)
    @Log
    public Response getStudent(@PathParam("id") String id) {

        Student student = studentService.getStudentById(id);

        if (student == null) {
            throw new NotFoundException();
        }

        return Response.status(Response.Status.OK)
                .entity(student)
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @CacheControlNoCache
    public Response insertStudent(@NotNull @Valid Student student) {

        studentService.insertStudent(student);

        return Response.status(Response.Status.OK)
                .entity(student)
                .build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @CacheControlNoCache
    public Response updateStudent(@NotNull @Valid Student student) throws AppException {

        studentService.updateStudent(student);

        return Response.status(Response.Status.OK)
                .entity(student)
                .build();
    }

}
