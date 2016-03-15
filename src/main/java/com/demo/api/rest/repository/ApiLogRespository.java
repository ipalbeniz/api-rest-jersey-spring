package com.demo.api.rest.repository;


import com.demo.api.rest.model.log.ApiLog;

public interface ApiLogRespository {

    void save(ApiLog apiLog);
}
