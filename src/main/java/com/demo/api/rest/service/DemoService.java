package com.demo.api.rest.service;

import com.demo.api.rest.model.DemoObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Cacheable(value="demoObjectsById", key="#id")
    public DemoObject getDemoObjectById(int id) {

        DemoObject demoObject = new DemoObject();

        demoObject.setId(id);
        demoObject.setName("demo");

        return demoObject;
    }
}
