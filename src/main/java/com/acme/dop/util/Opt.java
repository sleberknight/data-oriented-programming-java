package com.acme.dop.util;

import java.util.function.Function;

public sealed interface Opt<T> {

    record Some<T>(T value) implements Opt<T> { }
    record None<T>() implements Opt<T> { }

    static <T> Some<T> of(T value) {
        return new Some<>(value);
    }

    static <T> None<T> empty() {
        return new None<>();
    }

    default <U> Opt<U> map(Function<T, U> mapper) {
        return map(this, mapper);
    }

    static <T, U> Opt<U> map(Opt<T> opt, Function<T, U> mapper) {
        return switch (opt) {
            case Some<T>(var v) -> new Some<>(mapper.apply(v));
            case None<T>() -> new None<>();
        };
    }
}
