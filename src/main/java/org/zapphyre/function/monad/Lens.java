package org.zapphyre.function.monad;

import lombok.Value;

import java.util.function.Function;

@Value
public class Lens<T> {

    T instance;

    public static <T> Lens<T> l(T instance) {
        return new Lens<>(instance);
    }

    public <R> Lens<R> f(Function<? super T, ? extends R> focus) {
        return new Lens<>(focus.apply(instance));
    }

    public <R> R r(Function<? super T, ? extends R> focus) {
        return focus.apply(instance);
    }

    public T result() {
        return instance;
    }
}
