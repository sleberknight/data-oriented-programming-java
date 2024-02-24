package com.acme.dop.json;

import java.util.List;
import java.util.Map;

sealed interface JsonValue {
    record JsonString(String s) implements JsonValue { }

    record JsonNumber(double d) implements JsonValue { }

    record JsonNull() implements JsonValue { }

    record JsonBoolean(boolean b) implements JsonValue { }

    record JsonArray(List<JsonValue> values) implements JsonValue { }

    record JsonObject(Map<String, JsonValue> pairs) implements JsonValue { }
}
