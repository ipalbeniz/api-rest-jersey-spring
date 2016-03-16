package com.demo.api.rest.model.log;


import com.demo.api.rest.model.ModelItem;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ApiLog extends ModelItem {

    private ApiLogRequest request;
    private ApiLogResponse response;
    private Long processTime;

    public Long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
    }

    public ApiLogRequest getRequest() {
        return request;
    }

    public void setRequest(ApiLogRequest request) {
        this.request = request;
    }

    public ApiLogResponse getResponse() {
        return response;
    }

    public void setResponse(ApiLogResponse response) {
        this.response = response;
    }
}
