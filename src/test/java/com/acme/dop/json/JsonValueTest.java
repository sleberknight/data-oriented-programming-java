package com.acme.dop.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.acme.dop.json.JsonValue.JsonArray;
import com.acme.dop.json.JsonValue.JsonBoolean;
import com.acme.dop.json.JsonValue.JsonNumber;
import com.acme.dop.json.JsonValue.JsonObject;
import com.acme.dop.json.JsonValue.JsonString;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class JsonValueTest {

    @Test
    void shouldPatternMatch() {
        var john = createJohn();

        //noinspection ConstantValue
        if (john instanceof JsonObject(var pairs)
                && pairs.get("name") instanceof JsonString(String name)
                && pairs.get("age") instanceof JsonNumber(double age)
                && pairs.get("city") instanceof JsonString(String city)
                && pairs.get("employed") instanceof JsonBoolean(boolean employed)) {

            assertAll(
                    () -> assertThat(name).isEqualTo("John"),
                    () -> assertThat(age).isEqualTo(30.0),
                    () -> assertThat(city).isEqualTo("New York"),
                    () -> assertThat(employed).isTrue()
            );
        }
    }

    // { "name":"John", "age": 30, "city": "New York", "employed": true }
    private static JsonObject createJohn() {
        var nameNode = new JsonString("John");
        var ageNode = new JsonNumber(30.0);
        var cityNode = new JsonString("New York");
        var employedNode = new JsonBoolean(true);
        return new JsonObject(Map.of(
                "name", nameNode,
                "age", ageNode,
                "city", cityNode,
                "employed", employedNode
        ));
    }

    @Test
    void shouldMatchArrays() {
        var alex = createAlex();

        //noinspection ConstantValue
        if (alex instanceof JsonObject(var pairs)
                && pairs.get("nicknames") instanceof JsonArray(List<JsonValue> values)) {

            var nicknames = values.stream()
                    .filter(jsonValue -> jsonValue instanceof JsonString)
                    .map(JsonString.class::cast)
                    .map(JsonString::s)
                    .toList();

            assertThat(nicknames).containsExactly("Alex", "Alexa", "Lexi");
        }
    }

    // { "name": "Alexandria", "nicknames": [ "Alex", "Alexa", "Lexi" ] }
    private static JsonObject createAlex() {
        var nameNode = new JsonString("Alexandria");
        var nicknamesNode = new JsonArray(List.of(
                new JsonString("Alex"),
                new JsonString("Alexa"),
                new JsonString("Lexi")
        ));

        return new JsonObject(Map.of(
                "name", nameNode,
                "nicknames", nicknamesNode
        ));
    }
}
