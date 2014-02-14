package com.zenika.tryy;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * Convert the given TryFunction<I, O> instance as a Function<I, Try<O>> instance.
     * @param function
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Function<I, Try<O>> of(TryFunction<I, O> function) {
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
     * Return a map collector partitioning tries by type (Type.SUCCESS / Type.FAILURE).
     * @param <E>
     * @return
     */
    public static <E> Collector<Try<E>, ?, Map<Type, List<Try<E>>>> groupingBySuccess() {
        return Collectors.groupingBy(t -> t.getType());
    }

    /**
     * Consume the given stream of tries until all values have been retrieved or a failure has been detected.
     * If every type of those tries is SUCCESS, return a success wrapping the list of results.
     * Otherwise return the first failure detected.
     * @param stream
     * @param <E>
     * @return
     */
    public static <E> Try<List<E>> consume(Stream<Try<E>> stream) {
        Iterator<Try<E>> iterator = stream.iterator();
        List<E> successResults = new LinkedList<>();

        while (iterator.hasNext()) {
            Try<E> result = iterator.next();
            if (result.isSuccess()) {
                successResults.add(result.quietGet());
            } else {
                return (Try<List<E>>) result;
            }
        }

        return new Success<>(successResults);
    }

    /**
     * Map a stream with the given try-function, then consume the results until all values have been retrieved or a failure has been detected.
     * If every type of those tries is SUCCESS, return a success wrapping the list of results.
     * Otherwise return the first failure detected.
     * @param stream
     * @param tryFunction
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Try<List<O>> mapAndConsume(Stream<I> stream, TryFunction<I, O> tryFunction) {
        return consume(stream.map(of(tryFunction)));
    }

    /**
     * Map a stream with the given try-function, then consume the results until all values have been retrieved or a failure has been detected.
     * If every type of those tries is SUCCESS, return a success wrapping the list of results.
     * Otherwise return the first failure detected.
     * @param collection
     * @param tryFunction
     * @param <I>
     * @param <O>
     * @return
     */
    public static <I, O> Try<List<O>> mapAndConsume(Collection<I> collection, TryFunction<I, O> tryFunction) {
        return mapAndConsume(collection.stream(), tryFunction);
    }

    /**
     * Return the type associated to the current instance.
     * @return an reference to Type.SUCCESS or Type.FAILURE
     */
    public abstract Type getType();

    /**
     * Return, if it exists, the result wrapped in the current instance
     * @return the wrapped result
     * @throws Exception if the current instance is not a success
     */
    public abstract E get() throws Exception;

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
        return flatMap(result -> mapper.apply(result));
    }

    /**
     * If the current instance represents a success, transform its result and return it as a Try.
     * Otherwise return the current instance.
     * @param mapper the function used to transform the result
     * @param <F>
     * @return
     */
    public <F> Try<F> flatMap(TryFunction<E, F> mapper) {
        return isSuccess() ? Try.of(mapper).apply(quietGet()) : (Try<F>) this;
    }

    /**
     * If the current instance represents a success, wrap its result as an option.
     * Otherwise return Optional.empty().
     * @return
     */
    public Optional<E> toOption() {
        return isSuccess() ? Optional.of(quietGet()) : Optional.<E>empty();
    }

    /**
     * Equivalent to the get() method, but wrap the eventual exception inside a runtime exception.
     * This is a commodity function and it should be call ONLY if the type of the given instance is SUCCESS.
     * @return
     */
    private E quietGet() {
        try {
            return get();
        } catch (Exception e) {
            throw new RuntimeException(e); // this should never happen
        }
    }
}
