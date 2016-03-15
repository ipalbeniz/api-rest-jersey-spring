package com.demo.api.rest.config;

import com.demo.api.rest.config.spring.SpringMongoConfig;
import com.demo.api.rest.mock.service.StudentServiceMock;
import com.demo.api.rest.repository.ApiLogRespository;
import com.demo.api.rest.repository.ApiLogRespositoryImpl;
import com.demo.api.rest.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringMongoConfig.class})
public class SpringApiTestingContextConfig {

    @Bean
    StudentService studentService() {
        return new StudentServiceMock();
    }

    @Bean
    ApiLogRespository apiLogRespository() {
        return new ApiLogRespositoryImpl();
    }
}
