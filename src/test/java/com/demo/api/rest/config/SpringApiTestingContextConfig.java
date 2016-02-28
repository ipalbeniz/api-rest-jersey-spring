package com.demo.api.rest.config;

import com.demo.api.rest.service.StudentService;
import com.demo.api.rest.service.mock.StudentServiceMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringApiTestingContextConfig {

    @Bean
    public StudentService studentService() {
        return new StudentServiceMock();
    }
}
