package com.demo.api.rest.resource;

import com.demo.api.rest.config.jersey.annotations.CacheControlMaxAge;
import com.demo.api.rest.config.jersey.annotations.CacheControlNoCache;
import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.error.AppException;
import com.demo.api.rest.model.DemoObject;
import com.demo.api.rest.service.DemoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Component
@Path("/demo")
public class DemoResource {

    @Autowired
    private DemoService demoService;

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @CacheControlMaxAge(time = 60, unit = TimeUnit.SECONDS)
    public Response getDemoObject(@Max(5) @PathParam("id") Integer id, @NotNull @QueryParam("param") String param) throws AppException {

        if (StringUtils.equals(param, "unchecked")) {
            throw new NullPointerException("Unchecked error");
        } else if (StringUtils.equals(param, "checked")) {
            throw new AppException(AppErrorCatalog.DEMO_BUSINESS_ERROR);
        }

        DemoObject demoObject = demoService.getDemoObjectById(id);

        return Response.status(200)
                .entity(demoObject)
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @CacheControlNoCache
    public Response postDemoObject(@NotNull @Valid DemoObject demoObject) throws AppException {

        return Response.status(200).build();
    }

}
