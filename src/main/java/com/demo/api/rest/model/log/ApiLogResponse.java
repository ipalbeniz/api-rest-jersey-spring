package com.demo.api.rest.model.log;

import com.mongodb.DBObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document
public class ApiLogResponse {

    private Date dateTime;
    private Integer httpStatusCode;
    private String mediaType;
    private Map<String, String> headers;
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

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
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
