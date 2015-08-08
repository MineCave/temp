package com.intel.pulse.handlers;

import com.google.gson.Gson;
import com.intel.pulse.Request;
import com.intel.pulse.handlers.*;
import com.intel.pulse.plugin.Plugin;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;

public class RequestHandler implements HttpHandler {
    private Plugin plugin;

    public RequestHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String jsonInfo = new BufferedReader(new InputStreamReader(exchange.getRequestBody())).readLine();
        Request request = new Gson().fromJson(jsonInfo, Request.class);

        plugin.handle(request, exchange);
    }
}
