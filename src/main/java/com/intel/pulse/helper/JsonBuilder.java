package com.intel.pulse.helper;

import java.lang.StringBuilder;

public class JsonBuilder {

    private static final String OPEN_KEY = "\"";
    private static final String CLOSE_KEY = "\"";
    private static final String COLON = ":";
    private static final String COMMA = ",";

    private StringBuilder builder;

    public JsonBuilder() {
        builder = new StringBuilder();
    }

    public StringBuilder getStringBuilder() {
        return builder;
    }

    public String removeTrailingComma() {
        String string = getStringBuilder().toString();
        return string.length() == 0 ? "" : string.substring(0, string.length() - 1);
    }

    public String build() {
        return "{" + removeTrailingComma() + "}";
    }

    public String build(JsonBuilder... builders) {
        if (builders.length == 0)
            return build();

        if (builders.length > 1) {
            for (int i = 1; i < builders.length; i++) {
                builder.append(builders[i - 1].removeTrailingComma());
            }
        }

        builder.append(builders[0].getStringBuilder().toString());

        return build();
    }

    public JsonBuilder key(String key) {
        getStringBuilder().append(OPEN_KEY + key + CLOSE_KEY);
        return this;
    }

    public JsonBuilder val(Object val) {
        getStringBuilder().append(COLON + val + COMMA);
        return this;
    }

    public JsonBuilder keyVal(String key, Object val) {
        return key(key).val(val);
    }
}
