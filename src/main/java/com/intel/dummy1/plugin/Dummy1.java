package com.intel.dummy1.plugin;

import com.intel.pulse.plugin.collector.CollectorPlugin;
import com.intel.pulse.plugin.collector.PluginMetricType;
import com.intel.pulse.cpolicy.ConfigPolicyTree;

public class Dummy1 extends CollectorPlugin {

    public Dummy1(int version) {
        super("Dummy1", version);
    }

    public PluginMetricType[] collectMetrics(PluginMetricType[] metrics) {
        return metrics;
    }

    public PluginMetricType[] getMetricTypes() {
        PluginMetricType m1 = new PluginMetricType(new String[] { "intel", "dummy", "foo" }, 1);
        PluginMetricType m2 = new PluginMetricType(new String[] { "intel", "dummy", "bar" }, 1);
        return new PluginMetricType[] { m1, m2 };
    }

    public ConfigPolicyTree getConfigPolicyTree() {
        return new ConfigPolicyTree();
    }
}
