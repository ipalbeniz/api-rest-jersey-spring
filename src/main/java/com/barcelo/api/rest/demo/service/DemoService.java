package com.barcelo.api.rest.demo.service;

import com.barcelo.api.rest.demo.model.DemoObject;
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
