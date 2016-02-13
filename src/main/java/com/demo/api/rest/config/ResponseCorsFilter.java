package com.demo.api.rest.config;

import javax.annotation.Resource;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

@Resource
public class ResponseCorsFilter implements ContainerResponseFilter {
 
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {

        MultivaluedMap<String, Object> headers = response.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
 
}