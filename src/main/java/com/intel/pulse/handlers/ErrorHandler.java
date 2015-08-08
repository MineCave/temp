package com.intel.pulse.handlers;

import com.intel.pulse.Response;
import com.intel.pulse.Request;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class ErrorHandler extends Handler {

    public ErrorHandler(HttpExchange exchange, Request request) {
        super(exchange, request);
    }

    @Override
    protected void handle() throws IOException {
        Response response = new Response(request);

        response.setError("\"Method not supported\"");

        String responseString = response.toString();

        exchange.sendResponseHeaders(404, responseString.length());
        exchange.getResponseBody().write(responseString.getBytes());

        exchange.close();
    }
}
