package com.intel.pulse;

import com.intel.pulse.helper.JsonBuilder;

public class Response {

    private int id;
    private String method;
    private String error;
    private Object result;

    public Response(Request request) {
        setId(request.getId());
        setMethod(request.getMethod());
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String toString() {
        JsonBuilder builder = new JsonBuilder();

        builder.keyVal("error", getError());
        builder.keyVal("id", getId());
        builder.keyVal("result", getResult());

        return builder.build();
    }
}
