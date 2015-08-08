package com.intel.pulse.plugin;

import com.intel.pulse.Request;
import com.intel.pulse.Server;
import com.intel.pulse.helper.JsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Plugin {

    /**
     * Gets the plugin name
     * 
     * @return name of the plugin
     */
    String getName();

    /**
     * Gets the plugin version
     *
     * @return the version of the plugin
     */
    int getVersion();

    /**
     * Gets the type of plugin (publisher/processor/publisher)
     *
     * @return the plugin's type
     */
    PluginType getType();

    /**
     * Gets the Server (which holds HttpServer) that this plugin is using
     *
     * @return Server currently used by this plugin
     */
    Server getServer();

    /**
     * Gets the base JSON that every plugin uses
     *
     * @return basic JSON needed by every plugin to load
     */
    JsonBuilder getBodyJson();

    /**
     * Gets any plugin-specific JSON that is not included by default
     *
     * @return plugin-specific JSON needed for loading
     */
    JsonBuilder getExtraJson();

    /**
     * Gets full in String format JSON for Pulse to load the plugin
     * <br>
     * This is a combination of #getBodyJson() and #getExtraJson()
     *
     * @return JSON used by pulse to load plugin
     */
    String getFormedJson();

    /**
     * Loads the plugin by dumping #getFormedJson() to console
     */
    void load();

    /**
     * Handles an incoming request
     * <br>
     * Different types of plugins may need to do this differently.
     * 
     * @param request the Request that was received
     * @param exchange the HttpExchange passed to HttpServer#handle()
     *
     * @throws IOException
     */
    void handle(Request request, HttpExchange exchange) throws IOException;

}
