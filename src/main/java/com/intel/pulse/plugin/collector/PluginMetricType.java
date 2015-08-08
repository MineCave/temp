package com.intel.pulse.plugin.collector;

import com.google.gson.Gson;

import java.lang.StringBuilder;

public class PluginMetricType {
    private String[] namespace;
    private int version;

    public PluginMetricType(String[] namespace, int version) {
        this.namespace = namespace;
        this.version = version;
    }

    public String[] getNamespace() {
        return namespace;
    }

    public String getNamespacePath() {
        StringBuilder builder = new StringBuilder();

        for (String element : namespace) {
            builder.append(element);
            builder.append("/");
        }

        String path = builder.toString();
        return path.isEmpty() ? path : path.substring(0, path.length() - 1);
    }

    public int getVersion() {
        return version;
    }

    public void setNamespace(String[] namespace) {
        this.namespace = namespace;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "[namespace=" + getNamespacePath() + ",version=" + getVersion() + "]";
    }
}
