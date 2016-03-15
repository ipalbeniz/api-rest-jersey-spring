package com.demo.api.rest.error.mapping;

import com.demo.api.rest.error.AppErrorCatalog;
import com.demo.api.rest.model.error.ApiErrorMessage;
import com.fasterxml.jackson.core.JsonParseException;

import javax.ws.rs.ext.ExceptionMapper;

public class JsonParseExceptionMapper extends AbstractExceptionMapper<JsonParseException> implements ExceptionMapper<JsonParseException> {

    @Override
    protected ApiErrorMessage getApiErrorMessage(JsonParseException exception) {
        return ApiErrorMessage.valueOf(AppErrorCatalog.PARSE_ERROR);
    }
}
