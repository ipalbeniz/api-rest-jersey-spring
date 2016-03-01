package com.demo.api.rest.config.swagger;

import io.swagger.jaxrs.config.BeanConfig;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SwaggerConfig extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        BeanConfig beanConfig = new BeanConfig();

        beanConfig.setVersion("1");
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setTitle("Students REST API Demo");
        beanConfig.setDescription("Sample REST API");
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("com.demo.api.rest.resource");
        beanConfig.setScan(true);
    }

}