package com.barcelo.api.rest.demo.resource;

import com.barcelo.api.rest.demo.error.AppException;
import com.barcelo.api.rest.demo.model.DemoObject;
import com.barcelo.api.rest.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/demo")
public class DemoResource {

    @Autowired
    private DemoService demoService;

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDemoObject(@PathParam("id") Integer id, @QueryParam("exception") String exception) throws AppException {

        if ("unchecked".equals(exception)) {
            throw new NullPointerException("Unchecked error");
        } else if ("checked".equals(exception)) {
            throw new AppException(AppException.ApiError.INTEGRATION_ERROR);
        }

        DemoObject demoObject = demoService.getDemoObject(id);

        return Response.status(200)
                .entity(demoObject)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .allow("OPTIONS").build();
    }

}
