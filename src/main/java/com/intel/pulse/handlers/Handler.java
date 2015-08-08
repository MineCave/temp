package com.intel.pulse.handlers;

import com.intel.pulse.Request;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class Handler {
    protected HttpExchange exchange;
    protected Request request;

    public Handler(HttpExchange exchange, Request request) {
        this.exchange = exchange;
        this.request = request;
        try { handle(); }
        catch (IOException e) { System.out.println("Error handling request"); e.printStackTrace(); }
    }

    protected abstract void handle() throws IOException;
}
