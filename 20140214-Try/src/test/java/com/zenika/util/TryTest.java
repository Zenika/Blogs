package com.zenika.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class TryTest {

    private final Exception exception = new Exception("bug");

    @Test
    public void ofSuccessTest() {
        Function<Integer,Try<Integer>> fn = Try.<Integer, Integer>of(i -> i + 1);
        assertEquals(new Success(2), fn.apply(1));
    }

    @Test
    public void ofFailureTest() throws Exception {
        Function<Integer, Try<Integer>> fn = Try.<Integer, Integer>of(i -> { throw exception; });
        assertTrue(fn.apply(1).isFailure());
    }

    @Test
    public void getResultTest() {
        Success<Integer> success = new Success<>(1);
        assertEquals(Integer.valueOf(1), success.getResult());
    }

    @Test
    public void getExceptionTest() {
        Failure<Integer> failure = new Failure<>(exception);
        assertEquals(exception, failure.getException());
    }

    @Test
    public void mapSuccessTest() throws Exception {
        Try<Integer> success1 = new Success<>(1);
        Try<Double> success2 = success1.map(i -> 1.0);
        assertEquals(Double.valueOf(1.0), success2.asSuccess().getResult());
    }

    @Test
    public void mapFailureTest() {
        Try<Integer> failure1 = new Failure<>(exception);
        Try<Double> failure2 = failure1.map(i -> 1.0);
        assertSame(failure1, failure2);
    }

    @Test
    public void flatMapSuccessTest() throws Exception {
        Try<Integer> success1 = new Success<>(1);
        Try<Double> success2 = success1.flatMap(Try.of(i -> 1.0));
        assertEquals(Double.valueOf(1.0), success2.asSuccess().getResult());
    }

    @Test
    public void flatMapFailureTest() throws Exception {
        Try<Integer> success = new Success<>(1);
        Try<Integer> failure = success.flatMap(Try.of(i -> { throw exception; }));

        assertSame(exception, failure.asFailure().getException());
    }

    @Test
    public void toOptionSuccessTest() {
        Try<Integer> success = new Success<>(1);
        Optional<Integer> option = success.toOption();
        assertEquals(Integer.valueOf(1), option.get());
    }

    @Test
    public void toOptionFailureTest() {
        Try<Integer> success;
        success = new Failure<>(exception);
        Optional<Integer> option = success.toOption();
        assertFalse(option.isPresent());
    }

    private Integer throwingModulo(Integer i) throws Exception {
        if (i % 2 == 0) {
            return i;
        } else {
            throw exception;
        }
    }

    @Test(expected = ArithmeticException.class)
    public void ofRuntimeExceptionsShouldBeThrownTest() {
        Function<Integer, Try<Integer>> of = Try.of(i -> i / 0);
        Try<Integer> result = of.apply(1);
    }

    @Test
    public void groupingBySuccessTest() {
        Success<Integer> success = new Success<>(1);
        Failure<Integer> failure = new Failure<>(exception);
        List<Try<Integer>> tries = Arrays.asList(success, failure);

        Map<Try.Type, List<Try<Integer>>> tryMap = tries.stream().collect(Try.groupingBySuccess());

        assertEquals(Arrays.asList(success), tryMap.get(Try.Type.SUCCESS));
        assertEquals(Arrays.asList(failure), tryMap.get(Try.Type.FAILURE));
    }

    @Test
    public void tryLazyOfFailureTest() {
        Try<List<Integer>> result = Arrays.asList(0, 1, 2).stream().
                map(Try.lazyOf(this::throwingModulo)).
                collect(Try.collect());

        assertTrue(result.isFailure());
    }

    @Test
    public void tryLazyOfTest() {
        AtomicInteger atomicInteger = new AtomicInteger();
        Function<AtomicInteger,Supplier<Try<Integer>>> lazyFunction = Try.lazyOf(AtomicInteger::incrementAndGet);
        Supplier<Try<Integer>> supplier = lazyFunction.apply(atomicInteger);
        assertEquals(0, atomicInteger.get());
        Try<Integer> integerTry = supplier.get();
        assertTrue(integerTry.isSuccess());
        assertEquals(Integer.valueOf(1), integerTry.asSuccess().getResult());
    }

    @Test
    public void tryConsumeSuccessTest() {
        Try<List<Integer>> result = Arrays.asList(0, 2, 4).stream().
                map(Try.lazyOf(this::throwingModulo)).
                collect(Try.collect());

        assertTrue(result.isSuccess());
        assertEquals(Arrays.asList(0, 2, 4), result.asSuccess().getResult());
    }

    @Test
    public void tryConsumeFailureTest() {
        Try<List<Integer>> result = Arrays.asList(0, 2, 5).stream().
                map(Try.lazyOf(this::throwingModulo)).
                collect(Try.collect());

        assertTrue(result.isFailure());
    }

    @Test
    public void isPresentSuccessTest() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Success<AtomicInteger> success = new Success<>(atomicInteger);
        success.ifPresent(input -> input.incrementAndGet());
        assertEquals(1, success.getResult().get());
    }
}
