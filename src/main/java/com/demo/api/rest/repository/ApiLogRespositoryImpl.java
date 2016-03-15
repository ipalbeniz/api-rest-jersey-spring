package com.demo.api.rest.repository;

import com.demo.api.rest.model.log.ApiLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ApiLogRespositoryImpl implements ApiLogRespository {

    @Value("${mongo.collection.apilogs}")
    private String collectionName;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(ApiLog apiLog) {
        mongoTemplate.save((apiLog), collectionName);
    }

}
