package com.intel.pulse;

import com.intel.pulse.handlers.RequestHandler;
import com.intel.pulse.plugin.Plugin;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private Plugin plugin;
    private InetSocketAddress address;
    private HttpServer server;
    private Executor executor;

    public Server(Plugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        // Create an HTTP server with an address of 127.0.0.1:62191
        try {
            findAddress();
        } catch (IOException e) {
            System.out.println("Error starting HTTP server: " + e.getMessage());
            return;
        }

        // Set the path so that JSON calls will have somewhere to go.
        RequestHandler handler = new RequestHandler(plugin);
        server.createContext("/", handler);

        // Create a fixed pool of 10 threads for the HTTP server to use.
        executor = Executors.newFixedThreadPool(10);
        server.setExecutor(executor);

        // Start the HTTP server.
        server.start();
    }

    private void findAddress() throws IOException { // TODO: Find open port instead of hardcoding.
        address = new InetSocketAddress("127.0.0.1", 62191);
        server = HttpServer.create(address, 0);
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public HttpServer getServer() {
        return server;
    }

    // TODO: Call this method whenever the plugin is unloaded?
    private void end() {
        if (server != null) {
            server.stop(0);
        }
    }
}

