package com.intel.pulse.plugin;

public class PluginType {
    public static final PluginType
        COLLECTOR = new PluginType("Collector", 0),
        PROCESSOR = new PluginType("Processor", 1),
        PUBLISHER = new PluginType("Publisher", 2);

    private final String name;
    private final int value;

    public PluginType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String name() {
        return name;
    }

    public int value() {
        return value;
    }
}
