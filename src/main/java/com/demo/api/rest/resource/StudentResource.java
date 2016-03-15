package com.demo.api.rest.resource;

import com.demo.api.rest.config.jersey.annotations.CacheControlMaxAge;
import com.demo.api.rest.config.jersey.annotations.CacheControlNoCache;
import com.demo.api.rest.config.jersey.annotations.GZip;
import com.demo.api.rest.config.jersey.annotations.Log;
import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.Student;
import com.demo.api.rest.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.net.URI;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Component
@Path(StudentResource.STUDENT_BASE_RESOURCE_PATH)
@Api(value = StudentResource.STUDENT_BASE_RESOURCE_PATH, description = "Operations about students")
public class StudentResource extends ApiResource {

	public static final String STUDENT_BASE_RESOURCE_PATH = "/student";
    public static final String STUDENT_BY_ID_RESOURCE_PATH = "/{id}";

    @Autowired
    private StudentService studentService;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @GZip
    @Log
    @ApiOperation(value = "Get students",
            notes = "Returns a list of students",
            response = Student.class,
            responseContainer = "List"
    )
    @ApiResponses(value = { @ApiResponse(code = 500, message = "Internal server error") })
    public Response getStudents() {

        Collection<Student> students = studentService.getStudents();

		return Response.ok(students)
				.build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @CacheControlMaxAge(time = 60, unit = TimeUnit.SECONDS)
    @Log
    @ApiOperation(value = "Get student by id",
            notes = "Finds a student by id",
            response = Student.class
    )
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal server error")
            , @ApiResponse(code = 404, message = "Student not found")})
    public Response getStudent(@ApiParam(value = "Student id", required = true) @PathParam("id") String id) {

        Student student = studentService.getStudentById(id);

        if (student == null) {
            throw new NotFoundException();
        }

		return Response.ok(student)
				.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Log
    @CacheControlNoCache
    @ApiOperation(value = "Create a new student",
            notes = "Creates a new student",
            response = Student.class
    )
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal server error")
            , @ApiResponse(code = 400, message = "Invalid student received")})
    public Response insertStudent(@ApiParam(value = "Student information", required = true) @NotNull @Valid Student student) {

        studentService.insertStudent(student);

		URI entityURI = getEntityURI(student.getId(), true);
		
		return Response.created(entityURI).entity(student).build();
    }

	public static URI getEntityURI(@NotNull @Valid String studentId, boolean withApiBasePath) {
		String path = STUDENT_BASE_RESOURCE_PATH + STUDENT_BY_ID_RESOURCE_PATH.replace("{id}", studentId);
		
		if (withApiBasePath) {
			path = API_BASE_PATH + path;
		}
		
		return URI.create(path);
	}

    @PUT
    @Path(STUDENT_BY_ID_RESOURCE_PATH)
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @Log
    @CacheControlNoCache
    @ApiOperation(value = "Updates a student",
            notes = "Updates an existing student",
            response = Student.class
    )
    @ApiResponses(value = {@ApiResponse(code = 500, message = "Internal server error")
            , @ApiResponse(code = 400, message = "Invalid student received")
            , @ApiResponse(code = 404, message = "Student not found")})
    public Response updateStudent(@ApiParam(value = "Student id", required = true) @NotNull @PathParam("id") String id, @ApiParam(value = "Student information", required = true) @NotNull @Valid Student student) throws AppException {

        Student existingStudent = studentService.getStudentById(id);

        if (existingStudent == null) {
            throw new NotFoundException();
        }

        // Avoid updating the student if the student already has the received information
        student.setId(id);
        if (!student.equals(existingStudent)) {
            studentService.updateStudent(student);
        }

        return Response.ok(student).build();
    }

}
