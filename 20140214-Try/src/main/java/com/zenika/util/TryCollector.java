package com.zenika.util;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.zenika.util.Try.Type.FAILURE;
import static com.zenika.util.Try.Type.SUCCESS;

/**
 * A Collector implementation processing elements as long as results are success
 * @param <E>
 */
public class TryCollector<E> implements Collector<Supplier<Try<E>>, Map<Try.Type, LinkedList<Try<E>>>, Try<List<E>>> {

    @Override
    public Supplier<Map<Try.Type, LinkedList<Try<E>>>> supplier() {
        return () -> {
            HashMap<Try.Type, LinkedList<Try<E>>> map = new HashMap<>();
            map.put(SUCCESS, new LinkedList<>());
            map.put(FAILURE, new LinkedList<>());
            return map;
        };
    }

    @Override
    public BiConsumer<Map<Try.Type, LinkedList<Try<E>>>, Supplier<Try<E>>> accumulator() {
        return (results, supplier) -> {
            if(results.get(FAILURE).isEmpty()) {
                Try<E> result = supplier.get();
                results.get(result.getType()).add(result);
            }
        };
    }

    @Override
    public BinaryOperator<Map<Try.Type, LinkedList<Try<E>>>> combiner() {
        return (left, right) -> {
            if(!left.get(FAILURE).isEmpty()) {
                return left;
            }

            if(!right.get(FAILURE).isEmpty()) {
                return right;
            }

            left.get(SUCCESS).addAll(right.get(SUCCESS));
            return left;
        };
    }

    @Override
    public Function<Map<Try.Type, LinkedList<Try<E>>>, Try<List<E>>> finisher() {
        return results -> {
            if(results.get(FAILURE).isEmpty()) {
                List<E> collect = results.get(SUCCESS).stream().
                        map(success -> success.asSuccess().getResult()).
                        collect(Collectors.toList());

                return new Success<>(collect);

            } else {
                return (Failure<List<E>>) results.get(FAILURE).pop();
            }
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return new HashSet<>(0);
    }
}
