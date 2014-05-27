package com.zenika.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A instance of a Try represents an attempt to compute a value. A Try is either a success, either a failure.
 * @param <E>
 */
public abstract class Try<E> {

    /**
     * Enumerate the different types of a Try
     */
    public enum Type {
        SUCCESS, FAILURE
    }

    /**
     * Convert the given ThrowingFunction<I, O> instance as a Function<I, Try<O>> instance.
     * @param function
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Function<I, Try<O>> of(ThrowingFunction<I, O> function) {
        return input -> {
            try {
                O result = function.apply(input);
                return new Success<>(result);
            } catch (RuntimeException e) {
                throw e; // we don't want to wrap runtime exceptions
            } catch (Exception e) {
                return new Failure<>(e);
            }
        };
    }

    /**
     * Convert the given ThrowingFunction<I, O> instance as a Function<I, Supplier<Try<O>>> instance.
     * @param function
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Function<I, Supplier<Try<O>>> lazyOf(ThrowingFunction<I, O> function) {
        Function<I, Try<O>> of = of(function);

        return input -> {
            return () -> of.apply(input);
        };
    }

    /**
     * Return a map collector partitioning tries by type (Type.SUCCESS / Type.FAILURE).
     * @param <E>
     * @return
     */
    public static <E> Collector<Try<E>, ?, Map<Type, List<Try<E>>>> groupingBySuccess() {
        return Collectors.groupingBy(t -> t.getType());
    }

    /**
     * A collector for consuming stream of Try suppliers until all values have been retrieved or a failure has been detected.
     * If all values are success, return the list wrapped in an instance of Success
     * Otherwise return the first failure detected.
     * @param <E>
     * @return
     */
    public static <E> TryCollector<E> collect() {
        return new TryCollector<>();
    }


    /**
     * Return the type associated to the current instance.
     * @return an reference to Type.SUCCESS or Type.FAILURE
     */
    public abstract Type getType();

    /**
     * Return true if the current instance represents a success.
     * @return
     */
    public boolean isSuccess() {
        return getType() == Type.SUCCESS;
    }

    /**
     * Return true if the current instance represents a failure.
     * @return
     */
    public boolean isFailure() {
        return !isSuccess();
    }

    /**
     * If the current instance represents a success, transform its result and return it as a Try.
     * Otherwise return the current instance.
     * @param mapper the function used to transform the result
     * @param <F>
     * @return
     */
    public <F> Try<F> map(Function<E, F> mapper) {
        return flatMap(of(result -> mapper.apply(result)));
    }

    /**
     * If the current instance represents a success, transform its result and return it as a Try.
     * Otherwise return the current instance.
     * @param mapper the function used to transform the result
     * @param <O>
     * @return
     */
    public <O> Try<O> flatMap(Function<E, Try<O>> mapper) {
        return isSuccess() ? mapper.apply(asSuccess().getResult()) : (Try<O>) this;
    }

    /**
     * If the current instance represents a success, wrap its result as an option.
     * Otherwise return Optional.empty().
     * @return
     */
    public Optional<E> toOption() {
        return isSuccess() ? Optional.ofNullable(asSuccess().getResult()) : Optional.<E>empty();
    }

    /**
     * If the current instance represents a success, return the wrapped value.
     * Otherwise throw the wrapped exception.
     * @return
     * @throws Exception
     */
    public E getOrThrow() throws Exception {
        if(isSuccess()) {
            return asSuccess().getResult();
        } else {
            throw asFailure().getException();
        }
    }

    /**
     * Execute the given consumer if the current instance represents a success
     * @param consumer
     */
    public void ifPresent(Consumer<E> consumer) {
        if(isSuccess()) {
            consumer.accept(asSuccess().getResult());
        }
    }

    /**
     * Force this try as an instance of Success
     * @return
     */
    public Success<E> asSuccess() {
        return (Success<E>) this;
    }

    /**
     * Force this try as an instance of Failure
     * @return
     */
    public Failure<E> asFailure() {
        return (Failure<E>) this;
    }
}
