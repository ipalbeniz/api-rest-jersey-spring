package com.barcelo.api.rest.demo.resource;

import com.barcelo.api.rest.demo.error.ApiErrorCatalog;
import com.barcelo.api.rest.demo.error.AppException;
import com.barcelo.api.rest.demo.model.DemoObject;
import com.barcelo.api.rest.demo.service.DemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
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
    public Response getDemoObject(@Max(5) @PathParam("id") Integer id, @NotNull @QueryParam("param") String param) throws AppException {

        if (StringUtils.equals(param, "unchecked")) {
            throw new NullPointerException("Unchecked error");
        } else if (StringUtils.equals(param, "checked")) {
            throw new AppException(ApiErrorCatalog.DEMO_BUSINESS_ERROR);
        }

        DemoObject demoObject = demoService.getDemoObject(id);

        return Response.status(200)
                .entity(demoObject)
                .header("Access-Control-Allow-Headers", "X-extra-header")
                .allow("OPTIONS").build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getDemoObject(@NotNull @Valid DemoObject demoObject) throws AppException {

        return Response.status(200).build();
    }

}
