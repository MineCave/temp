package com.intel.pulse.plugin;

import com.google.gson.Gson;

import com.intel.pulse.Server;
import com.intel.pulse.Response;
import com.intel.pulse.helper.JsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public abstract class PluginBase implements Plugin {
    protected final String name;
    protected final int version;
    protected final PluginType type;

    protected Server server;
    protected JsonBuilder bodyJson;
    protected JsonBuilder extraJson;

    public PluginBase(String name, int version, PluginType type) {
        this.name = name;
        this.version = version;
        this.type = type;

        bodyJson = new JsonBuilder();
        extraJson = new JsonBuilder();
        JsonBuilder  metaJson = new JsonBuilder();
        Gson gson = new Gson();

        metaJson.keyVal("Name", gson.toJson(getName()));
        metaJson.keyVal("Version", getVersion());
        metaJson.keyVal("Type", getType().value());
        metaJson.keyVal("RPCType", 1); // TODO: un-hardcode
        metaJson.keyVal("AcceptedContentTypes", gson.toJson(new String[] {"pulse.gob"})); // TODO: un-hardcode
        metaJson.keyVal("ReturnedContentTypes", gson.toJson(new String[] {"pulse.gob"})); // TODO: un-hardcode
        bodyJson.keyVal("Meta", metaJson.build());
        bodyJson.keyVal("RPCType", 1);
        bodyJson.keyVal("Token", gson.toJson("Mvh3p2YKbGlh5Z55WooJjp98ALEj95wfrrdF2Cxcs-c="));
        bodyJson.keyVal("Type", getType().value());
        bodyJson.keyVal("State", 0);
        bodyJson.keyVal("ErrorMessage", gson.toJson(""));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public PluginType getType() {
        return type;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public JsonBuilder getBodyJson() {
        return bodyJson;
    }

    @Override
    public JsonBuilder getExtraJson() {
        return extraJson;
    }

    @Override
    public String getFormedJson() {
        return new JsonBuilder().build(getBodyJson(), getExtraJson());
    }

    @Override
    public void load() {
        server = new Server(this);
        server.start();
        getBodyJson().keyVal("ListenAddress", server.getAddress());
        System.out.println(getFormedJson());
    }

    protected void sendResponse(Response response, HttpExchange exchange) throws IOException {
        String responseString = response.toString();

        exchange.sendResponseHeaders(200, responseString.length());
        exchange.getResponseBody().write(responseString.getBytes());

        exchange.close();
    }
}
