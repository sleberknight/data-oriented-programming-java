package com.acme.dop.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OptTest {

    @Test
    void shouldMapSome() {
        var str = "forty-two";
        Opt<String> optString = Opt.of(str);

        Opt<Integer> optInt = optString.map(s -> s.length());
        Integer result = switch (optInt) {
            case Opt.Some<Integer>(Integer i) -> i;
            case Opt.None<Integer>() -> 42;
        };

        assertThat(result).isEqualTo(str.length());
    }

    @Test
    void shouldMapNone() {
        Opt<String> optString = Opt.empty();

        Opt<Integer> optInt = optString.map(s -> s.length());
        Integer result = switch (optInt) {
            case Opt.Some<Integer>(Integer i) ->i;
            case Opt.None<Integer>() -> 42;
        };

        assertThat(result).isEqualTo(42);
    }
}
