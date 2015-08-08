package com.intel.pulse.plugin.collector;

import com.google.gson.Gson;
import com.intel.pulse.helper.ISO8601DateParser;
import com.intel.pulse.helper.JsonBuilder;
import com.intel.pulse.handlers.PingHandler;
import com.intel.pulse.handlers.CollectMetricsHandler;
import com.intel.pulse.handlers.GetMetricTypesHandler;
import com.intel.pulse.handlers.GetConfigPolicyTreeHandler;
import com.intel.pulse.handlers.ErrorHandler;
import com.intel.pulse.Request;
import com.intel.pulse.Response;
import com.intel.pulse.cpolicy.ConfigPolicyTree;
import com.intel.pulse.plugin.PluginBase;
import com.intel.pulse.plugin.PluginType;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CollectorPlugin extends PluginBase {
    
    public CollectorPlugin(String name, int version) {
        super(name, version, PluginType.COLLECTOR);
    }

    public abstract PluginMetricType[] collectMetrics(PluginMetricType[] metrics);
    public abstract PluginMetricType[] getMetricTypes();
    public abstract ConfigPolicyTree getConfigPolicyTree();

    @Override
    public void handle(Request request, HttpExchange exchange) throws IOException {
        String method = request.getMethod();
        switch (method) {
            case "Collector.Ping":
                sendResponse(getPingResponse(request), exchange);
                break;
            case "Collector.CollectMetrics":
                sendResponse(getCollectMetricsResponse(request), exchange);
                break;
            case "Collector.GetMetricTypes":
                sendResponse(getMetricTypesResponse(request), exchange);
                break;
            case "Collector.GetConfigPolicyTree":
                sendResponse(getConfigPolicyTreeResponse(request), exchange);
                break;
            default:
                new ErrorHandler(exchange, request);
        }
    }

    private Response getPingResponse(Request request) {
        Response response = new Response(request);

        response.setResult("{\"PluginMetrics\":null}");

        return response;
    }

    private Response getCollectMetricsResponse(Request request) {
        Response response = new Response(request);

        PluginMetricResponse[] retMetrics = new PluginMetricResponse[request.getParams().length];

        for (int i = 0; i < request.getParams().length; i++) {
            String param = new Gson().toJson(request.getParams()[i]);
            PluginMetricType[] metrics = new Gson().fromJson(param, PluginMetricType[].class);
            collectMetrics(metrics);
            Object config = null; // TODO: Implement config (ConfigDataNode under pulse/core/data in Golang)
            int data = 7;
            String date = ISO8601DateParser.toString(new Date());
            String[] namespace = new String[] { "mem", "active" };
            int version = 0;
            retMetrics[i] = new PluginMetricResponse(config, data, date, namespace, version);
        }

        response.setResult(new JsonBuilder().keyVal("PluginMetrics", new Gson().toJson(retMetrics)).build());

        return response;
    }

    private class PluginMetricResponse {
        private Object config;
        private int data;
        private String lastTime;
        private String[] namespace;
        private int version;

        public PluginMetricResponse(Object config, int data, String lastTime, String[] namespace, int version) {
            this.config = config;
            this.data = data;
            this.lastTime = lastTime;
            this.namespace = namespace;
            this.version = version;
        }
    }

    private Response getMetricTypesResponse(Request request) {
        Response response = new Response(request);

        PluginMetricTypesResponse[] metrics = new PluginMetricTypesResponse[1];
        Object config = null; // TODO: Implement config (ConfigDataNode under pulse/core/data in Golang)
        Object data = null;
        String date = ISO8601DateParser.toString(new Date());
        String[] namespace = new String[] { "foo", "bar" };
        int version = 0;
        metrics[0] = new PluginMetricTypesResponse(config, data, date, namespace, version);

        response.setResult(new JsonBuilder().keyVal("PluginMetricTypes", new Gson().toJson(metrics)).build());

        return response;
    }

    private class PluginMetricTypesResponse {
        private Object config;
        private Object data;
        private String lastTime;
        private String[] namespace;
        private int version;

        public PluginMetricTypesResponse(Object config, Object data, String lastTime, String[] namespace, int version) {
            this.config = config;
            this.data = data;
            this.lastTime = lastTime;
            this.namespace = namespace;
            this.version = version;
        }
    }

    private Response getConfigPolicyTreeResponse(Request request) {
        Response response = new Response(request);

        Map<String, Map<String, Object>> responseMap = new HashMap<String, Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("keys", new String[] { "foo" });
        root.put("node", null);
        List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
        Map<String, Object> nodesMap = new HashMap<String, Object>();
        nodesMap.put("keys", new String[] { "bar" });
        Map<String, Object> node = new HashMap<String, Object>();
        Map<String, Object> rules = new HashMap<String, Object>();
        Map<String, Object> name = new HashMap<String, Object>();
        Map<String, Object> nameDefault = new HashMap<String, Object>();
        nameDefault.put("Value", "bob");
        name.put("default", nameDefault);
        name.put("key", "name");
        name.put("required", false);
        name.put("type", "string");
        rules.put("name", name);
        Map<String, Object> password = new HashMap<String, Object>();
        password.put("default", null);
        password.put("key", "password");
        password.put("required", true);
        password.put("type", "string");
        rules.put("password", password);
        Map<String, Object> someInt = new HashMap<String, Object>();
        Map<String, Object> someIntMap = new HashMap<String, Object>();
        someIntMap.put("Value", 100);
        someInt.put("default", someIntMap);
        rules.put("someInt", someInt);
        Map<String, Object> somefloat = new HashMap<String, Object>();
        Map<String, Object> somefloatMap = new HashMap<String, Object>();
        somefloatMap.put("Value", 3.14);
        somefloat.put("default", somefloatMap);
        somefloat.put("key", "somefloat");
        somefloat.put("required", false);
        somefloat.put("type", "float");
        rules.put("somefloat", somefloat);
        node.put("rules", rules);
        nodesMap.put("nodes", null);
        nodesMap.put("node", node);
        nodes.add(nodesMap);
        root.put("nodes", nodes);
        map.put("ctree", root);
        responseMap.put("PolicyTree", map);

        response.setResult(new Gson().toJson(responseMap));

        return response;
    }
}
