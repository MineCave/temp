package com.intel.dummy1;

import com.intel.dummy1.plugin.Dummy1;

public class Main {

    public static void main(String[] args) {
        // Start the plugin with version set to 1.
        new Dummy1(1).load();
    }
}
