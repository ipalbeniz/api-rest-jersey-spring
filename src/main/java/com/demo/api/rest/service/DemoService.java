package com.demo.api.rest.service;

import com.demo.api.rest.model.DemoObject;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public DemoObject getDemoObject(int id) {

        DemoObject demoObject = new DemoObject();

        demoObject.setId(id);
        demoObject.setName("demo");

        return demoObject;
    }
}
