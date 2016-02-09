package com.barcelo.api.rest.demo.model;


import javax.validation.constraints.NotNull;

public class DemoObject {

    @NotNull
    private Integer id;
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
