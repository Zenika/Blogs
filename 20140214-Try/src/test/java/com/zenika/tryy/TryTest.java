package com.zenika.tryy;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

public class TryTest {

    private final Exception exception = new Exception("bug");

    @Test
    public void getSuccessTest() throws Exception {
        Try<Integer> success = new Success<>(1);
        assertEquals(Integer.valueOf(1), success.get());
    }

    @Test(expected = Exception.class)
    public void getFailureTest() throws Exception {
        Try<Integer> failure = new Failure<>(exception);
        failure.get();
    }

    @Test
    public void ofSuccessTest() {
        Function<Integer,Try<Integer>> fn = Try.<Integer, Integer>of(i -> i + 1);
        assertEquals(new Success(2), fn.apply(1));
    }

    @Test(expected = Exception.class)
    public void ofFailureTest() throws Exception {
        Function<Integer, Try<Integer>> fn = Try.<Integer, Integer>of(i -> { throw exception; });
        fn.apply(1).get();
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
        assertEquals(Double.valueOf(1.0), success2.get());
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
        Try<Double> success2 = success1.flatMap(i -> 1.0);
        assertEquals(Double.valueOf(1.0), success2.get());
    }

    @Test(expected = Exception.class)
    public void flatMapFailureTest() throws Exception {
        Try<Integer> success = new Success<>(1);
        Try<Integer> failure = success.flatMap(i -> {
            throw exception;
        });
        failure.get();
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

    @Test
    public void orFailSuccess() throws Exception {
        Try<List<Integer>> success = Try.consume(Arrays.asList(0, 1, 2).stream().
                map(i -> new Success<>(i + 1)));

        assertEquals(Arrays.asList(1, 2, 3), success.get());
    }

    @Test(expected = Exception.class)
    public void orFailFailure() throws Exception {
        Try<List<Integer>> failure = Try.consume(Arrays.asList(0, 1, 2).stream().
                map(i -> {
                    if (i % 2 == 0) {
                        return new Success<>(i);
                    } else {
                        return new Failure<>(exception);
                    }
                }));

        failure.get();
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
}
