package com.intel.pulse;

public class Request {
    private int id;
    private String method;
    private Object[] params;

    public Request(int id, String method, Object[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }
}
