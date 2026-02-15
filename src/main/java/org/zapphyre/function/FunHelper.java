package org.zapphyre.function;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@UtilityClass
public class FunHelper {

    public static <T> BinaryOperator<T> laterMerger() {
        return (q, p) -> p;
    }

    public static <T, R> R optToNull(T id, Function<T, Optional<R>> function) {
        return Optional.ofNullable(id)
                .flatMap(function)
                .orElse(null);
    }

    public static <T, R> Consumer<T> eat(Function<T, R> mapper) {
        return mapper::apply;
    }

    public static <T, R> Consumer<T> chew(Function<T, R> mapper, Consumer<R> sink) {
        return t -> sink.accept(mapper.apply(t));
    }

    public static <T> Function<T, T> funky(Consumer<T> consumer) {
        return q -> Stream.of(q)
                .filter(Objects::nonNull)
                .peek(consumer)
                .findAny()
                .orElse(q);
    }

    @SafeVarargs
    public static <T> Consumer<T> pipe(Consumer<T>... consumers) {
        return q -> Arrays.stream(consumers).forEach(p -> p.accept(q));
    }

    public static <T, R> Function<T, Consumer<Consumer<R>>> spit(Function<T, R> mapper) {
        return q -> p -> p.accept(mapper.apply(q));
    }

    @SafeVarargs
    public static Handover<String> glob(Consumer<Consumer<String>>... delegates) {
        return consumer -> Arrays.stream(delegates).forEach(delegate -> delegate.accept(consumer));
    }

    public static <T> Function<Optional<T>, T> orNull() {
        return opt -> opt.orElse(null);
    }

    @SafeVarargs
    public static <T> Consumer<T> logFun(String msg, Object ...args) {
        return q -> {
            Object[] combined = new Object[args.length + 1];
            combined[0] = q;
            System.arraycopy(args, 0, combined, 1, args.length);

            log.info(msg, combined);
        };
    }

    @SafeVarargs
    public static <T, C extends Collection<T>> C concat(C initial, C... additionals) {
        Arrays.stream(additionals).forEach(initial::addAll);
        return initial;
    }


    public static <T> T throwUp() {
        throw new RuntimeException();
    }

    public interface Handover<T> {
        void to(Consumer<T> consumer);
    }
}
