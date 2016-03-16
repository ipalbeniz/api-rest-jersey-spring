package com.demo.api.rest.model.log;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document
public class ApiLogRequest {

    private Date dateTime;
    private String path;
    private String method;
    private String mediaType;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;
    private DBObject entity;
    private String unparsedEntity;

    public String getUnparsedEntity() {
        return unparsedEntity;
    }

    public void setUnparsedEntity(String unparsedEntity) {
        this.unparsedEntity = unparsedEntity;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public DBObject getEntity() {
        return entity;
    }

    public void setEntity(DBObject entity) {
        this.entity = entity;
    }
}
